package it.eng.tim.profilo.integration.proxy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import it.eng.tim.profilo.integration.client.DCAClient;
import it.eng.tim.profilo.integration.client.SDPClient;
import it.eng.tim.profilo.model.integration.dca.AuthProfileRequest;
import it.eng.tim.profilo.model.integration.dca.AuthProfileResponse;
import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;


@Component
@Slf4j
class DCAProxyDelegate {

    private DCAClient dcaClient;
    
    
    @Autowired
    DCAProxyDelegate(DCAClient client) {
        this.dcaClient = client;
    }


    @HystrixCommand(fallbackMethod = "reliableProfileInfo")
    ResponseEntity<AuthProfileResponse> getAuthProfile(AuthProfileRequest request) {
    	log.debug("DCAProxyDelegate.getAuthProfile ("+request+")");
        return dcaClient.getAuthProfile(request);
    }
    
    //FALLBACK
    @SuppressWarnings("unused")
    ResponseEntity<AuthProfileResponse> reliableProfileInfo(AuthProfileRequest request, Throwable throwable) {
        return ProxyTemplate.getFallbackResponse(throwable);
    }
    
    
    
}
