package it.eng.tim.profilo.model.configuration;

import org.junit.Test;

import it.eng.tim.profilo.model.configuration.Constants;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by alongo on 30/04/18.
 */
public class ConstantsTest {

    @Test
    public void SubsystemsNotContainsNullOrEmty(){
        assertFalse(Constants.Subsystems.contains(""));
        assertFalse(Constants.Subsystems.contains(null));
    }

    @Test
    public void SubsystemsNotContainsMissing(){
        assertFalse(Constants.Subsystems.contains("some_value"));
    }

    @Test
    public void SubsystemsContainsIgnoreCase(){
        assertTrue(Constants.Subsystems.contains("MYTIMAPP"));
        assertTrue(Constants.Subsystems.contains("MYTIMWEB"));
        assertTrue(Constants.Subsystems.contains("MyTimWeb"));
    }

}