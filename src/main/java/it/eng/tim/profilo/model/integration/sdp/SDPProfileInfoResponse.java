package it.eng.tim.profilo.model.integration.sdp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SDPProfileInfoResponse {


      private String id;
      private String rifCliente;
      private String tiid;      

      
      private String statoCertificazioneCliente;
      private String sourceSystem;

      
      private Profilo profilo;
      private Contatto contatto;
      
      private Documento documento;
      private List<Indirizzo> indirizzi;
      
}
