package com.medlife.deliveries.data.response.validationErrors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private HttpStatus status;
    private List<Error> errors;

    public ErrorResponse(){
        this.status = HttpStatus.BAD_REQUEST;
    }

}
