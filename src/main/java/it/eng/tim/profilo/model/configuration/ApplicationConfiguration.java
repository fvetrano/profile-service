package it.eng.tim.profilo.model.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@ConfigurationProperties(prefix = "application.config")
@Getter
@Setter
@ToString
@Component
public class ApplicationConfiguration {

	private String keystorePath;
	private String keystoreAlias;
	private String keystorePass;
	private String dcaResourceId;
	private String sourceSystem;
	
}
