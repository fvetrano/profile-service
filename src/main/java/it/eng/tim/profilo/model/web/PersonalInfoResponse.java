package it.eng.tim.profilo.model.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfoResponse {

    @ApiModelProperty(notes = "Outcome Result" , example = "OK")
    private String status;

    @ApiModelProperty(notes = "Ragione sociale" , example = "")
    private String business_name;    
    
    @ApiModelProperty(notes = "Nome" , example = "Mario")
    private String name;    

    @ApiModelProperty(notes = "Cognome" , example = "Rossi")
    private String surname;    

    @ApiModelProperty(notes = "Acconut login" , example = "mario.rossi@gmail.com")
    private String userName;    

    @ApiModelProperty(notes = "Mail di contatto" , example = "mario.rossi@gmail.com")
    private String contact_mail;    

    @ApiModelProperty(notes = "Telefono di contatto" , example = "3337898777")
    private String contact_number;    

    //@ApiModelProperty(notes = "ID Telecom" , example = "123456789")
    //private String tiid;    
    
    @ApiModelProperty(notes = "Tipologia di account" , example = "ACCOUNT_UNICO")
    private String account_type;    

    @ApiModelProperty(notes = "Data ultima  modifica password ()" , example = "")
    private String date_modifypsw;    

    @ApiModelProperty(notes = "url portale servizi " , example = "https://www.tim.it/")
    private String url_alice;    

    @ApiModelProperty(notes = "Account collegato a Facebook (true/false)" , example = "true")
    private boolean isAccountFBLinked;    
 


}
