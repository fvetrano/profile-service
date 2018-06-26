package it.eng.tim.profilo.model.integration.dca;

import java.util.List;

import it.eng.tim.profilo.model.integration.sdp.Contatto;
import it.eng.tim.profilo.model.integration.sdp.Documento;
import it.eng.tim.profilo.model.integration.sdp.Indirizzo;
import it.eng.tim.profilo.model.integration.sdp.Profilo;
import it.eng.tim.profilo.model.integration.sdp.SDPProfileInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthProfileResponse {

	private String status;

	private List<ProfileParameterOutput> profileParameterOutputExposedServiceBeans;
	


	
}
