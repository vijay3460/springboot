package com.medlife.deliveries.exceptions;

import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class FieldValidations {

    private String fieldName;
    private String className;
    private String message;
    private String correctValues;

    public FieldValidations(){}

    public FieldValidations(String fieldName,String className,String message,String correctValues){
        super();
        this.fieldName = fieldName;
        this.className = className;
        this.message = message;
        this.correctValues = correctValues;

    }

}
