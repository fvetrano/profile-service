package it.eng.tim.profilo.integration.proxy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


import it.eng.tim.profilo.integration.client.SDPClient;
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
class SDPProxyDelegate {

    private SDPClient sdpClient;

    
    @Autowired
    SDPProxyDelegate(SDPClient creditClient) {
        this.sdpClient = creditClient;
    }


    @HystrixCommand(fallbackMethod = "reliableProfileInfo")
    ResponseEntity<SDPProfileInfoResponse> getProfileInfo(String rifCliente, @RequestHeader HttpHeaders headers) {
    	log.debug("SDPProxyDelegate.getProfileInfo ("+rifCliente+")");
        return sdpClient.getProfileInfo(rifCliente, headers);
    }
    
    //FALLBACK
    @SuppressWarnings("unused")
    ResponseEntity<SDPProfileInfoResponse> reliableProfileInfo(String rifCliente, HttpHeaders headers, Throwable throwable) {
        return ProxyTemplate.getFallbackResponse(throwable);
    }
    
    
    
}
