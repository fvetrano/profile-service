package it.eng.tim.profilo.model.integration.dca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DCACookie {

	public static final String DCAUTH_AUTH_COOKIE_NAME = "DCAUTH_AUTH_COOKIE";
	
	
	private String name = DCAUTH_AUTH_COOKIE_NAME;
	private String value;
	
}
