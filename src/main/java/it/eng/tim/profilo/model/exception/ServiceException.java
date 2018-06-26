package it.eng.tim.profilo.model.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by alongo on 16/04/18.
 */
public class ServiceException extends Exception {

    private final String outcome;
    private String message;
    
    
    public ServiceException(String message, String outcome) {
        super(message);
        this.outcome = outcome;
        this.message = message;
    }

    public String getErrorCode(){
    	return outcome;
    }

    public String getErrorMessage(){
    	return message;
    }

    public HttpStatus getErrorStatus(){
        if (outcome.equals("AAA111")) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


}
