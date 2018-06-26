package it.eng.tim.profilo.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import it.eng.tim.profilo.model.domain.ProfileInfo;
import it.eng.tim.profilo.model.exception.BadRequestException;
import it.eng.tim.profilo.model.exception.NotAuthorizedException;
import it.eng.tim.profilo.model.exception.ServiceException;
import it.eng.tim.profilo.model.web.ErrorResponse;
import it.eng.tim.profilo.model.web.PersonalInfoResponse;
import it.eng.tim.profilo.service.ProfileService;
import it.eng.tim.profilo.util.ExceptionUtil;
import it.eng.tim.profilo.validation.ProfileControllerValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/profile")
@Api("Controller exposing profile operations")
@Slf4j
//@SwaggerDefinition(tags = { @Tag(name = "My Swagger Resource", description = "Meaningful stuff in here") })
public class ProfileController {

    private ProfileService profileService;
    //private StaticContentService staticContentService;
    
    private static final int GAP = 10000;
    private static final Random __randomCounter = new Random();
    
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
        //this.staticContentService = staticContentService;
    }

    
    @RequestMapping(method = RequestMethod.GET, value = "/getPersonalInfo" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get personal profile information", response = PersonalInfoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "User not authenticated", response = ErrorResponse.class),
            @ApiResponse(code = 408, message = "Timeout", response = ErrorResponse.class),
            @ApiResponse(code = 422, message = "Cannot parse source JSON", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Generic error", response = ErrorResponse.class),
            @ApiResponse(code = 503, message = "Service unavailable", response = ErrorResponse.class)
    })
//    public PersonalInfoResponse getPersonalInfo(@RequestHeader HttpHeaders headers) 
    public PersonalInfoResponse getPersonalInfo(@RequestHeader HttpHeaders headers, 
    											@RequestHeader(value = "businessID", required = false) String xBusinessId,    		
    											@RequestHeader(value = "messageID", required = false) String xMessageID,    		
    											@RequestHeader(value = "transactionID", required = false) String xTransactionID,    		
    											@RequestHeader(value = "channel", required = false) String xChannel,    		
    											@RequestHeader(value = "sourceSystem", required = false) String xSourceSystem,    		
    											@RequestHeader(value = "sessionID", required = false) String xSessionID) 
    							throws ServiceException, NotAuthorizedException, BadRequestException {

    	
    	if(!ProfileControllerValidator.validate(headers)) {
    		throw new BadRequestException("Missing/Wrong headers in getPersonalInfo");
    	}
    	
		long start = System.currentTimeMillis();
		
    	String sessionJWTString = headers.getFirst("sessionJWT");
    	
    	log.debug("sessionJWTString=["+sessionJWTString+"]");
    	
    	String rifCliente = null;
    	String dcaAuthCookie = null;
    	String userAccount = null;
    	
    	if(sessionJWTString!=null) {
    		try {
            	Jwt token = JwtHelper.decode(sessionJWTString);
            	//token.verifySignature(arg0);
            	
            	String decodedToken = token.getClaims();
            	log.debug("decoded token=["+decodedToken+"]");
            	
            	HashMap sessionJWTMap = new ObjectMapper().readValue(decodedToken, HashMap.class);
            	log.debug("sessionJWTMap=["+sessionJWTMap+"]");
    			
            	rifCliente = (String)sessionJWTMap.get("cf_piva");
            	dcaAuthCookie = (String)sessionJWTMap.get("dcaCoockie");
            	userAccount = (String)sessionJWTMap.get("userAccount");
            	
            	if(rifCliente == null) {
        			throw new ServiceException("Error in getting rifCliente from sessionJWT", "ERR001");
            	}

            	if(dcaAuthCookie == null) {
        			throw new ServiceException("Error in getting dcaCoockie from sessionJWT", "ERR001");
            	}

            	
    		}
    		catch(Exception ex) {
    			log.error("Error in getting sessionJWT " + ex);
    			throw new BadRequestException("Error in getting sessionJWT");
    		}
    		
    	}
    	else {
    		//throw new BadRequestException("Missing sessionJWT");
    		throw new ServiceException("Missing sessionJWT","ERR001");
    	}
    	
    	try {
        	ProfileInfo profileInfo = profileService.getUserProfileInfo(rifCliente, dcaAuthCookie);
        	
        	
        	PersonalInfoResponse response = new PersonalInfoResponse();
        	response.setStatus("OK");
        	
        	//response.setTiid(profileInfo.getSdpProfileInfo().getTiid());
        	
        	response.setBusiness_name(profileInfo.getSdpProfileInfo().getBusinessName());
        	response.setName(profileInfo.getSdpProfileInfo().getName());
        	response.setSurname(profileInfo.getSdpProfileInfo().getSurname());
        	
        	response.setContact_mail(profileInfo.getSdpProfileInfo().getContactMail());
        	response.setContact_number(profileInfo.getSdpProfileInfo().getContactNumber());
        	
        	response.setAccount_type(profileInfo.getDcaProfileInfo().getAccountType());
        	
        	if(userAccount != null) {
        		response.setUserName(userAccount);
        	}
        	else {
        		response.setUserName(profileInfo.getDcaProfileInfo().getUserName());	
        	}
        	
        	response.setDate_modifypsw(profileInfo.getDcaProfileInfo().getDateModifyPwd());
        	
        	response.setUrl_alice(profileInfo.getSdpProfileInfo().getUrlAlice());
        	response.setAccountFBLinked(profileInfo.getDcaProfileInfo().isAccountFBLinked());
        	
        	long end = System.currentTimeMillis();
        	log.info("Service work time ["+(end-start)+"] ms - Return response " + response);
        	
        	return response;
    		
    	}
    	catch(ServiceException se) {
    		throw se;
    	}
    	catch(Exception ex) {
    		log.error("Error in getUserProfileInfo " + ExceptionUtil.getStackTrace(ex));
    		
    		throw new ServiceException("Internal Error", "ERR002");
    	}

    }

    
    
	public static String generateID( final String prefix) {
	
		final StringBuilder buffer = new StringBuilder( prefix );
		buffer.append("_");
		buffer.append( ( __randomCounter.nextInt( 99 ) + 1 ) * GAP);
		return buffer.toString();
	}	
    
}
