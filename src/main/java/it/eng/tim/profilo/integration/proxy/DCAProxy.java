package it.eng.tim.profilo.integration.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.tim.profilo.model.integration.dca.AuthProfileRequest;
import it.eng.tim.profilo.model.integration.dca.AuthProfileResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class DCAProxy extends ProxyTemplate {

    static final  String SUBSYSTEM_NAME = "Profile DCA Service";
    
    private DCAProxyDelegate delegate;

    @Autowired
    public DCAProxy(DCAProxyDelegate delegate) {
        this.delegate = delegate;
    }

    
    public AuthProfileResponse getAuthProfile(AuthProfileRequest request) {
    	
    	log.debug("DCAProxy.getAuthProfile ("+request+")");
    	return getBody(delegate.getAuthProfile(request));
    }
    

    @Override
    String getSubsystemName() {
        return SUBSYSTEM_NAME;
    }
    
    
}
