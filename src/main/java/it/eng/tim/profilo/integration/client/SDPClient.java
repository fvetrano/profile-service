package it.eng.tim.profilo.integration.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;


@FeignClient(
        name="profile-sdp",
        url = "${profile.integration.sdp-base-path}/"
)
public interface SDPClient {

    @GetMapping("clienti/{rifCliente}")
    ResponseEntity<SDPProfileInfoResponse> getProfileInfo(@PathVariable("rifCliente") String rifCliente, @RequestHeader HttpHeaders headers);


}
