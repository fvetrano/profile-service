package it.eng.tim.profilo.validation;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import it.eng.tim.profilo.model.configuration.Constants;

/**
 * Created by alongo on 30/04/18.
 */
public class ProfileControllerValidator {

    ProfileControllerValidator() {}

    /*
    public static boolean validate(ScratchCardRequest request){
        return request != null
                && !StringUtils.isEmpty(request.getCardNumber())
                && !StringUtils.isEmpty(request.getFromMsisdn())
                && !StringUtils.isEmpty(request.getToMsisdn())
                && Constants.Subsystems.contains(request.getSubSys());
    }
    */

    
    public static boolean validate(HttpHeaders headers){
        return headers != null
                && !StringUtils.isEmpty(headers.getFirst("sessionJWT"));
    }

}
