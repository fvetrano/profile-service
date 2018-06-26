package it.eng.tim.profilo.validation;

import org.junit.Test;

import it.eng.tim.profilo.model.configuration.Constants;
import it.eng.tim.profilo.validation.ProfileControllerValidator;

import static org.junit.Assert.*;

/**
 * Created by alongo on 30/04/18.
 */
public class TopupControllerValidatorTest {

    @Test
    public void validatePrivateConstructor() throws Exception {
        new ProfileControllerValidator();
    }

    
    
    @Test
    public void validateScratchCardRequestFailsOnNull() throws Exception {
        assertFalse(ProfileControllerValidator.validate(null));
    }

    

    /*
    @Test
    public void validateScratchCardRequestOk() throws Exception {
        ScratchCardRequest request = new ScratchCardRequest();
        request.setCardNumber("1111111111111");
        request.setFromMsisdn("3400000001");
        request.setToMsisdn("3400000002");
        request.setSubSys(Constants.Subsystems.MYTIMAPP.name());
        assertTrue(ProfileControllerValidator.validate(request));
    }

    @Test
    public void validateScratchCardRequestKoOnMissingParams() throws Exception {
        assertFalse(ProfileControllerValidator.validate(
                new ScratchCardRequest(null,"3400000001","3400000002",Constants.Subsystems.MYTIMAPP.name())));
        assertFalse(ProfileControllerValidator.validate(
                new ScratchCardRequest("1111111111111",null,"3400000002",Constants.Subsystems.MYTIMAPP.name())));
        assertFalse(ProfileControllerValidator.validate(
                new ScratchCardRequest("1111111111111","3400000001",null,Constants.Subsystems.MYTIMAPP.name())));
        assertFalse(ProfileControllerValidator.validate(
                new ScratchCardRequest("1111111111111","3400000001","3400000002",null)));
        assertFalse(ProfileControllerValidator.validate(
                new ScratchCardRequest("1111111111111","3400000001","3400000002","Some_Random_String")));
    }
	*/
}