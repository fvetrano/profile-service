package it.eng.tim.profilo.model.exception;

import lombok.Data;

/**
 * Created by alongo on 27/04/18.
 */
@Data
public class SubsystemException extends RuntimeException {

    private final String causeException;
    private final String causeMsg;
    private final String subsystem;

    public SubsystemException(String subsystem, String causeException, String causeMsg) {
        super();
        this.subsystem = subsystem;
        this.causeException = causeException;
        this.causeMsg = causeMsg;
    }

}
