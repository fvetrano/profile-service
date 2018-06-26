package it.eng.tim.profilo.model.integration.sdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profilo {

	private String tipoCliente;
	
	private String codiceAcli;
	private String stato;
	private String partitaIva;
	private String ragioneSociale;
	private String referenteLegale;
	
	private String nome;
	private String cognome;
	
	
}
