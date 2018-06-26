package it.eng.tim.profilo.web.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tim.profilo.model.exception.ServiceException;
import it.eng.tim.profilo.model.integration.dca.AuthProfileRequest;
import it.eng.tim.profilo.model.integration.dca.AuthProfileResponse;
import it.eng.tim.profilo.model.integration.dca.ProfileParameterOutput;

@RestController
@RequestMapping("dcauth/rest/auth")
public class DCAProfileMockController {

	private static final Logger logger = LoggerFactory.getLogger("profile");
	
    @RequestMapping(method = RequestMethod.POST, value = "/authentication/getAuthProfile")
    public AuthProfileResponse getAuthProfile(@RequestBody AuthProfileRequest request) throws ServiceException{
    	

    	logger.debug("MOCK DCA - getAuthProfile - request = ["+request+"]");
    	
    	
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
    	p3.setParameterName("flagIsSocial");
    	p3.setParameterValue("true");
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
    	
    	logger.debug("MOCK - RESPONSE = ["+response+"]");
    	
    	
    	try {
        	ObjectMapper mapper = new ObjectMapper();
        	logger.debug("MOCK - RESPONSE AS JSON = ["+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response)+"]");
    		
    	}
    	catch(Exception ex) {
    		logger.error("MOCK ERROR " + ex);
    	}
    	
    	
    	return response;
    }
	
    
        
}
