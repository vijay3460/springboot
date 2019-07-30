package com.medlife.deliveries.exceptions;

import lombok.Data;

import java.util.Set;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class ApiValidationException extends RuntimeException {

    private Set<FieldValidations> validations;
    private String message;

    public ApiValidationException(String message,Set<FieldValidations> validations){
        super(message);
        this.message = message;
        this.validations = validations;
    }
}
