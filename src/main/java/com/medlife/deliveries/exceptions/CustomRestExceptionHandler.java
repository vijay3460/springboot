package com.medlife.deliveries.exceptions;

import com.medlife.deliveries.data.response.validationErrors.Error;
import com.medlife.deliveries.data.response.validationErrors.ErrorResponse;
import com.medlife.deliveries.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiValidationException.class)
    public ResponseEntity<ErrorResponse> handleFieldConstraintViolation(ApiValidationException apiValidationException){
        List<Error> errors = new ArrayList<>();
        for(FieldValidations fieldValidation : apiValidationException.getValidations()){
            Error error = new Error(fieldValidation.getMessage(),fieldValidation.getFieldName());
            errors.add(error);
        }
        ErrorResponse apiErrors = new ErrorResponse();

        apiErrors.setErrors(errors);
        return new ResponseEntity<ErrorResponse>(apiErrors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handleSqlExceptions(PSQLException psqlException){
        ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
        ErrorResponse apiErrors = new ErrorResponse();
        List<Error> errors = new ArrayList<>();
        log.info("serverErrorMessage is : {}",MapperUtils.getJSONString(serverErrorMessage));
        errors.add(new Error(serverErrorMessage.getMessage(),serverErrorMessage.getColumn()));
        apiErrors.setErrors(errors);
        return new ResponseEntity<>(apiErrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException constraintViolationException){
        ErrorResponse apiErrors = new ErrorResponse();
        List<Error> errors = new ArrayList<>();

        Set<ConstraintViolation<?>> violationSet = constraintViolationException.getConstraintViolations();
        Iterator<ConstraintViolation<?>> it = violationSet.iterator();
        while(it.hasNext()){
            ConstraintViolation violation = it.next();
            errors.add(new Error(violation.getMessage(),violation.getPropertyPath().toString()));
        }
        apiErrors.setErrors(errors);
        return new ResponseEntity<>(apiErrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralExceptions(Exception e){
        printExceptionTypeClasses(e);
        int psqlExceptionIndex = ExceptionUtils.indexOfThrowable(e,PSQLException.class);
        log.info("psqlException Index is : {}",psqlExceptionIndex);
        if(psqlExceptionIndex!=-1)
        {
           return handleSqlExceptions((PSQLException) ExceptionUtils.getThrowableList(e).get(psqlExceptionIndex));
        }
        int constraintViolationIndex = ExceptionUtils.indexOfType(e, ConstraintViolationException.class);
        log.info("constraintViolation Index is : {}",constraintViolationIndex);
        if(constraintViolationIndex!=-1)
        {
            return handleConstraintViolationExceptions((ConstraintViolationException) ExceptionUtils.getThrowableList(e).get(constraintViolationIndex));
        }
        return ExceptionUtils.rethrow(e);
    }

    private void printExceptionTypeClasses(Throwable throwable){
        if(throwable == null)
            return;
        log.info("throwable is of type : {}",throwable.getClass().getName());
        printExceptionTypeClasses(throwable.getCause());
    }

}
