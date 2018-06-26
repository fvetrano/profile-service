package it.eng.tim.profilo.model.exception;

/**
 * Created by alongo on 30/04/18.
 */
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }
}
