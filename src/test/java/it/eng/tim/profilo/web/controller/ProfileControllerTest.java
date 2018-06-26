package it.eng.tim.profilo.web.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import it.eng.tim.profilo.integration.proxy.DCAProxy;
import it.eng.tim.profilo.integration.proxy.SDPProxy;
import it.eng.tim.profilo.model.configuration.BuiltInConfiguration;
import it.eng.tim.profilo.model.configuration.Constants;
import it.eng.tim.profilo.model.exception.BadRequestException;
import it.eng.tim.profilo.model.exception.ServiceException;
import it.eng.tim.profilo.model.exception.SubsystemException;
import it.eng.tim.profilo.model.integration.dca.AuthProfileResponse;
import it.eng.tim.profilo.model.integration.dca.ProfileParameterOutput;
import it.eng.tim.profilo.model.integration.sdp.Contatto;
import it.eng.tim.profilo.model.integration.sdp.Profilo;
import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;
import it.eng.tim.profilo.model.web.PersonalInfoResponse;
import it.eng.tim.profilo.service.ProfileService;
import it.eng.tim.profilo.web.ProfileController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
//Tested as in-service integration test
public class ProfileControllerTest {

    @Mock
    SDPProxy sdpProxy;

    @Mock
    DCAProxy dcaProxy;

    @Mock
    BuiltInConfiguration configuration;

    ProfileService profileService;
    ProfileController controller;

    @Before
    public void init(){
        profileService = new ProfileService(sdpProxy,dcaProxy);

        controller = new ProfileController(profileService);
    }

    @After
    public void cleanup(){
        Mockito.reset(sdpProxy, configuration);
    }


    
    @Test(expected = BadRequestException.class)
    public void getPersonalInfoKoOnInvalidRequest() throws Exception {
        controller.getPersonalInfo(null, null, null, null, null, null, null);
    }

    @Test(expected = ServiceException.class)
    public void getPersonalInfoKoOnSDPUnavailable() throws Exception {
        Mockito.when(sdpProxy.getProfileInfo(Mockito.anyString(), Mockito.any(HttpHeaders.class ))).thenThrow(new  SubsystemException("Auth service", "TestException", "a test error"));
    	
        //Mockito.when(sdpProxy.getProfileInfo(Mockito.anyString(), Mockito.anyObject()).thenThrow(new ServiceException("Auth service", "TestException"));
    	//Mockito.when(creditProxy.authorizeLineForRecharge(Mockito.anyString())).thenThrow(new SubsystemException("Auth service", "TestException", "a test error"));
        controller.getPersonalInfo(getValidHttpHeaders(),
        						   getValidHttpHeaders().getFirst("businessID"),
        						   getValidHttpHeaders().getFirst("messageID"),
        						   getValidHttpHeaders().getFirst("transactionID"),
								   getValidHttpHeaders().getFirst("channel"),
								   getValidHttpHeaders().getFirst("sourceSystem"),
        						   getValidHttpHeaders().getFirst("sessionID"));
    }

    
    
    @Test
    public void getPersonalInfoOk() throws Exception {
    	
    	
    	Mockito.when(sdpProxy.getProfileInfo(Mockito.anyString(), Mockito.any(HttpHeaders.class )))
        .thenReturn(buildSDPProfileInfoResponseForTest());
    	
    	Mockito.when(dcaProxy.getAuthProfile(Mockito.anyObject()))
        .thenReturn(buildAuthProfileResponseForTest());
    	
    	PersonalInfoResponse response = controller.getPersonalInfo(getValidHttpHeaders(),
																   getValidHttpHeaders().getFirst("businessID"),
																   getValidHttpHeaders().getFirst("messageID"),
																   getValidHttpHeaders().getFirst("transactionID"),
																   getValidHttpHeaders().getFirst("channel"),
																   getValidHttpHeaders().getFirst("sourceSystem"),
																   getValidHttpHeaders().getFirst("sessionID"));


        assertNotNull(response);
        assertNotNull(response.getStatus());
        assertEquals ("OK", response.getStatus());
        assertNotNull(response.getContact_mail());
        
        
    }

    
    private SDPProfileInfoResponse buildSDPProfileInfoResponseForTest() {
    	SDPProfileInfoResponse response = new SDPProfileInfoResponse();
    	response.setTiid("123456");

    	Contatto contatto = new Contatto();
    	contatto.setIndirizzoEmail("prova@example.com");
    	contatto.setRecapitoTelefonicoNumero("3331234567");
    	
    	response.setContatto(contatto);
    	
    	Profilo profilo = new Profilo();
    	profilo.setPartitaIva("PVIA11111");
    	profilo.setCognome("COGNOME");
    	profilo.setNome("NOME");
    	profilo.setReferenteLegale("REF MARIO ROSSI");
    	
    	response.setProfilo(profilo);
    	
    	return response;
    }
    
    private AuthProfileResponse buildAuthProfileResponseForTest() {
    	AuthProfileResponse response = new AuthProfileResponse();
    	
    	response.setStatus("OK");
    	
    	List<ProfileParameterOutput> params = new ArrayList<ProfileParameterOutput>();
    	
    	ProfileParameterOutput p1 = new ProfileParameterOutput();
    	p1.setParameterName("username");
    	p1.setParameterValue("mario.rossi@gmail.com");
    	p1.setServiceType("HeaderVar");

    	ProfileParameterOutput p2 = new ProfileParameterOutput();
    	p2.setParameterName("fiscalCode");
    	p2.setParameterValue("MARROS77L12H501V");
    	p2.setServiceType("HeaderVar");

    	ProfileParameterOutput p3 = new ProfileParameterOutput();
    	p3.setParameterName("isSocial");
    	p3.setParameterValue("false");
    	p3.setServiceType("HeaderVar");
    	
    	ProfileParameterOutput p4 = new ProfileParameterOutput();
    	p4.setParameterName("accountType");
    	p4.setParameterValue("ACCOUNT_UNICO");
    	p4.setServiceType("HeaderVar");
    	
    	ProfileParameterOutput p5 = new ProfileParameterOutput();
    	p5.setParameterName("lastModifyPassword");
    	p5.setParameterValue("28-feb-2018");
    	p5.setServiceType("HeaderVar");
    	
    	params.add(p1);
    	params.add(p2);
    	params.add(p3);
    	params.add(p4);
    	params.add(p5);
    	
    	response.setProfileParameterOutputExposedServiceBeans(params);    	

    	return response;
    }
    

    private HttpHeaders getValidHttpHeaders() {
    	HttpHeaders headers = new HttpHeaders();
    	
    	headers.add("sessionJWT", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImlzcyI6Imh0dHBzOi8vZHQtcy1hcGlndzAxLnRlbGVjb21pdGFsaWEubG9jYWw6ODQ0MyJ9.ew0KCSJ1c2VyQWNjb3VudCI6ICJwcmltZTFAdGltLml0IiwNCgkiY2ZfcGl2YSI6ICJaSk9SSkE2MkQyMUwyMTlQIiwNCgkiZGNhQ29vY2tpZSI6ICJaamRqWldRNFlXVXRORFV3TVMwME1HTmxMV0psWkdNdFlURXhabVZtT1RWbFpESm1YMTlmUkVOQlZWUklYMEZWVkVoZlEwOVBTMGxGWDE5ZkxuUnBiUzVwZEE9PSIsDQoJImFjY291bnRUeXBlIjogIkFDQ09VTlRfVU5JQ08iDQp9.t0dJFeFFF5v2FHZkI7y2ALqg4iAGav2_XSqFYzIFpOk");
    	
    	return headers;
    }
    
}