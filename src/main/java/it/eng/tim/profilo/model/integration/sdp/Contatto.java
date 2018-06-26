package it.eng.tim.profilo.model.integration.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contatto {

	private String indirizzoEmail;
	private String altroIndirizzoEmail;
	private String recapitoTelefonicoNumero;
	private String altroRecapitoNumero;
	private String faxNumero;

	


}
