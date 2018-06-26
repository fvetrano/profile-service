package it.eng.tim.profilo.integration.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class SDPProxy extends ProxyTemplate {

    static final  String SUBSYSTEM_NAME = "Profile SDP Service";
    
    private SDPProxyDelegate delegate;

    @Autowired
    public SDPProxy(SDPProxyDelegate delegate) {
        this.delegate = delegate;
    }

    
    public SDPProfileInfoResponse getProfileInfo(String rifCliente, @RequestHeader HttpHeaders headers) {
    	
    	log.debug("SDPProxy.getProfileInfo ("+rifCliente+")");
    	return getBody(delegate.getProfileInfo(rifCliente, headers));
    }
    

    @Override
    String getSubsystemName() {
        return SUBSYSTEM_NAME;
    }
}
