package com.medlife.deliveries.data.response.validationErrors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private String parameter;
    private String message;
    public Error(){ }

    public Error(String message,String parameter){
        this.message = message;
        this.parameter = parameter;
    }

}
