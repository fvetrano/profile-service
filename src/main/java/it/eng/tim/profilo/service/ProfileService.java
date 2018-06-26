package it.eng.tim.profilo.service;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import it.eng.tim.profilo.integration.proxy.DCAProxy;
import it.eng.tim.profilo.integration.proxy.SDPProxy;
import it.eng.tim.profilo.model.configuration.ProfileConstants;
import it.eng.tim.profilo.model.domain.DCAProfileInfo;
import it.eng.tim.profilo.model.domain.ProfileInfo;
import it.eng.tim.profilo.model.domain.SDPProfileInfo;
import it.eng.tim.profilo.model.exception.BadRequestException;
import it.eng.tim.profilo.model.exception.ServiceException;
import it.eng.tim.profilo.model.integration.dca.AuthProfileRequest;
import it.eng.tim.profilo.model.integration.dca.AuthProfileResponse;
import it.eng.tim.profilo.model.integration.dca.DCAConstants;
import it.eng.tim.profilo.model.integration.dca.DCACookie;
import it.eng.tim.profilo.model.integration.dca.ProfileParameterOutput;
import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;
import it.eng.tim.profilo.util.ExceptionUtil;
import it.eng.tim.profilo.model.exception.GenericException;
import it.eng.tim.profilo.model.exception.NotAuthorizedException;


@Service
@Slf4j
public class ProfileService {

    private SDPProxy sdpProxy;
    private DCAProxy dcaProxy;

    private static final Random __random = new Random();
    
    @Autowired
    public ProfileService(SDPProxy sdpProxy, DCAProxy dcaProxy) {
        this.sdpProxy = sdpProxy;
        this.dcaProxy = dcaProxy;
        
    }


    
    
    public ProfileInfo getUserProfileInfo(String rifCliente, String dcaAuthCookie) throws ServiceException{
    	
    	ProfileInfo info = new ProfileInfo();
    	
    	try {
    		
    		HttpHeaders headers = buildHttpHeaders();
    		
    		log.debug("Calling SDP ...");
    		long startSDP = System.currentTimeMillis();
    		SDPProfileInfoResponse sdpResponse = sdpProxy.getProfileInfo(rifCliente, headers);
    		long endSDP = System.currentTimeMillis();
    		log.info("SDP response in ["+(endSDP-startSDP)+"] ms - Result [" + sdpResponse + "]");
    		
    		
    		SDPProfileInfo sdpProfileInfo = new SDPProfileInfo();
    		sdpProfileInfo.setTiid(sdpResponse.getTiid());
    		sdpProfileInfo.setUrlAlice("https://www.tim.it");
    		
    		if(sdpResponse.getProfilo()!=null) {
    			sdpProfileInfo.setBusinessName(sdpResponse.getProfilo().getReferenteLegale());
        		sdpProfileInfo.setName(sdpResponse.getProfilo().getNome());
        		sdpProfileInfo.setSurname(sdpResponse.getProfilo().getCognome());

    		}

    		if(sdpResponse.getContatto() != null) {
        		sdpProfileInfo.setContactMail(sdpResponse.getContatto().getIndirizzoEmail());
        		sdpProfileInfo.setContactNumber(sdpResponse.getContatto().getRecapitoTelefonicoNumero());
    		}
    		
    		
    		//..........

    		AuthProfileRequest authRequest = new AuthProfileRequest();
    		authRequest.setResourceId(ProfileConstants.DCA_RESOURCE_ID);
    		authRequest.setTid(generateTid());
    		//authRequest.setSid(generateTid()); //opzionale
    		DCACookie cookie = new DCACookie();
    		cookie.setName(DCACookie.DCAUTH_AUTH_COOKIE_NAME);
    		cookie.setValue(dcaAuthCookie);
            authRequest.setCookie(cookie);

    		log.debug("Calling DCA ...");
    		long startDCA = System.currentTimeMillis();
    		long endDCA = System.currentTimeMillis();
    		AuthProfileResponse dcaResponse = dcaProxy.getAuthProfile(authRequest);
    		log.info("DCA response in ["+(endDCA-startDCA)+"] ms - Result [" + dcaResponse + "]");

    		
    		if(dcaResponse.getStatus()!=null && dcaResponse.getStatus().equalsIgnoreCase("ok")) {
        		Map<String,String> parametersMap = getProfileParameterMap(dcaResponse.getProfileParameterOutputExposedServiceBeans());
				
        		DCAProfileInfo dcaProfileInfo = new DCAProfileInfo();

        		dcaProfileInfo.setAccountType(parametersMap.get(DCAConstants.PARAM_ACCOUNT_TYPE));
        		dcaProfileInfo.setDateModifyPwd(parametersMap.get(DCAConstants.PARAM_LAST_MODIFY_PWD));
        		dcaProfileInfo.setUserName(parametersMap.get(DCAConstants.PARAM_USERNAME));
        		
        		String isSocial = parametersMap.get(DCAConstants.PARAM_IS_SOCIAL);
                if(isSocial!=null && isSocial.equalsIgnoreCase("true")) {
                    dcaProfileInfo.setAccountFBLinked(true);   
                }
                else {
                    dcaProfileInfo.setAccountFBLinked(false);
                }
        		
        		info.setDcaProfileInfo(dcaProfileInfo);
        		info.setSdpProfileInfo(sdpProfileInfo);
        		
        		return info;
    		}
    		else {
    			throw new ServiceException("Temporary Error", "ERR005");
    		}
    	}
    	catch(Exception ex) {
    		log.error("Error in getUserProfileInfo " + ExceptionUtil.getStackTrace(ex));
    		
    		throw new ServiceException("Temporary Error", "ERR001");	
    	}
    	
    }
    
    
    
    
    private Map<String,String> getProfileParameterMap(List<ProfileParameterOutput> params) {
    	Map parametersMap = new HashMap<String,String>();
    	
    	if(params != null) {
    		for(int i=0; i<params.size(); i++) {
    			
    			parametersMap.put(params.get(i).getParameterName(), params.get(i).getParameterValue());
    		}
    	}
    	
    	return parametersMap;
    }
    
    
    private HttpHeaders buildHttpHeaders() {
    	HttpHeaders headers = new HttpHeaders();
		Date now = new Date(System.currentTimeMillis());
    	
    	
    	headers.set("sourceSystem", ProfileConstants.SOURCE_SYS_ID);
    	headers.set("channel", ProfileConstants.CHANNEL_ID_APP);
    	headers.set("interactionDate-Date", getDate(now));
    	headers.set("interactionDate-Time", getTime(now));

    	/*
    	headers.set("sessionID", "session_id_123");
    	headers.set("businessID", "bid_123");
    	headers.set("transactionID", "trans_123");
    	*/
    	//generateID("tid");

    	headers.set("sessionID", generateID("session"));
    	headers.set("businessID", generateID("bid"));
    	headers.set("transactionID", generateID("tid"));

    	return headers;
    }

	private static String getTime(Date d){
		SimpleDateFormat sdfTime = new SimpleDateFormat ( "HH:mm:ss:SSS" );
		return sdfTime.format(d);
	}


	private static String getDate(Date d){
		SimpleDateFormat sdfDate = new SimpleDateFormat ( "yyyy-MM-dd" );
		return sdfDate.format(d);
	}
	
	
	private String generateTid() {
		return generateID("tid");
	}

	
	public static String generateID( final String prefix) {
		
		final StringBuilder buffer = new StringBuilder( prefix );
		buffer.append("_");
		buffer.append( ( __random.nextInt( 99 ) + 1 ) );
		return buffer.toString();
	}	
	
}
