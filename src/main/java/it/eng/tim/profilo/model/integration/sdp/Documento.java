package it.eng.tim.profilo.model.integration.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

	private String tipo;
	private String numero;
	private String rilasciatoDa;
	private String dataRilascio;
	private String dataScadenza;

	private String provinciaRilascio;
	private String comuneRilascio;


}
