package it.eng.tim.profilo.model.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by alongo on 02/05/18.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @ApiModelProperty(notes = "Error code")
    private String errorCode;
    @ApiModelProperty(notes = "Error message")
    private String errorMessage;

}
