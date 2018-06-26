package it.eng.tim.profilo.web.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tim.profilo.model.exception.ServiceException;
import it.eng.tim.profilo.model.integration.sdp.Contatto;
import it.eng.tim.profilo.model.integration.sdp.Profilo;
import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;

@RestController
@RequestMapping("clienti/")
public class SDPProfileMockController {

	private static final Logger logger = LoggerFactory.getLogger("profile");
	
    @RequestMapping(method = RequestMethod.GET, value = "{rifCliente}")
    public SDPProfileInfoResponse getUserProfileInfo(@PathVariable("rifCliente") String rifCliente, @RequestHeader HttpHeaders headers) 
    																										throws ServiceException{
    	
    	logger.debug("MOCK - getUserProfileInfo (clienti/{rifCliente}) rifCliente = ["+rifCliente+"]");
    	logger.debug("MOCK - HttpHeaders = ["+headers+"]");
    	
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
