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


import it.eng.tim.profilo.model.integration.dca.AuthProfileRequest;
import it.eng.tim.profilo.model.integration.dca.AuthProfileResponse;



@FeignClient(
        name="profile-dca",
        url = "${profile.integration.dca-base-path}/"
)
public interface DCAClient {

    @PostMapping("/dcauth/rest/auth/authentication/getAuthProfile")
    ResponseEntity<AuthProfileResponse> getAuthProfile(@RequestBody AuthProfileRequest request);


}
