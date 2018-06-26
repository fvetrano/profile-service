package it.eng.tim.profilo.model.integration.dca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileParameterOutput {

	private String parameterName;
	private String parameterValue;
	private String serviceType;
	

}
