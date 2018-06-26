package it.eng.tim.profilo.web;

import org.junit.Test;

import it.eng.tim.profilo.model.exception.*;
import it.eng.tim.profilo.web.RestControllerExceptionHandling;

import static org.junit.Assert.*;


public class RestControllerExceptionHandlingTest {

    private RestControllerExceptionHandling errorHandler = new RestControllerExceptionHandling();

    @Test
    public void handleGenericException() throws Exception {
        assertNotNull(errorHandler.handleGenericException(new GenericException("An exception", new RuntimeException("a cause")), null));
    }

    @Test
    public void handleBadRequestException() throws Exception {
        assertNotNull(errorHandler.handleBadRequestException(new BadRequestException("An exception"), null));
    }

    @Test
    public void handleSubsystemException() throws Exception {
        assertNotNull(errorHandler.handleSubsystemException(new SubsystemException("A Subsystem", "An exception", "An exception message"), null));
    }

    @Test
    public void handleNotAuthorizedException() throws Exception {
        assertNotNull(errorHandler.handleNotAuthorizedException(new NotAuthorizedException("An exception"), null));
    }

    @Test
    public void handleServiceException() throws Exception {
        assertNotNull(errorHandler.handleServiceException(new ServiceException("An exception", "KO"), null));
    }
    
    

}