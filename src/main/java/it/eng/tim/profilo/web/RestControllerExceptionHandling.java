package it.eng.tim.profilo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import it.eng.tim.profilo.model.exception.*;
import it.eng.tim.profilo.model.web.ErrorResponse;

/**
 * Created by alongo on 16/04/18.
 */
@ControllerAdvice
@Slf4j
public class RestControllerExceptionHandling {

    //SYSTEM ERRORS

    @ExceptionHandler(value = { GenericException.class })
    protected ResponseEntity<Object> handleGenericException(GenericException ex, WebRequest request) {
        log.error("An error occured " + ex);
        return new ResponseEntity<>(
                new ErrorResponse("RIC006", "Errore generico non è stato possibile portare a termine l’esecuzione del metodo"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("An error occured " + ex);
        return new ResponseEntity<>(
                new ErrorResponse("RIC002", "Parametri della richiesta mancanti o errati"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { SubsystemException.class })
    protected ResponseEntity<Object> handleSubsystemException(SubsystemException ex, WebRequest request) {
        log.error(String.format("failure '%s' on subsystem '%s': %s",
                ex.getCauseException(),
                ex.getSubsystem(),
                ex.getCauseMsg()));
        return new ResponseEntity<>(
                new ErrorResponse("RIC006", "Errore generico non è stato possibile portare a termine l’esecuzione del metodo"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //LOGIC ERRORS

    @ExceptionHandler(value = { NotAuthorizedException.class })
    protected ResponseEntity<Object> handleNotAuthorizedException(NotAuthorizedException ex, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse("RIC002", ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { ServiceException.class })
    protected ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
    	
        ErrorResponse body
                = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return new ResponseEntity<>(body, ex.getErrorStatus());
    }



}
