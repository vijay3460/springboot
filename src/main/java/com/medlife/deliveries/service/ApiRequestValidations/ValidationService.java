package com.medlife.deliveries.service.ApiRequestValidations;

import com.medlife.deliveries.exceptions.ApiValidationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;


/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface ValidationService<T,K> {

    void validateApiRequest(K apiRequestData) throws ApiValidationException,ConstraintViolationException;

    T getEntityFromRequest(K apiRequestData);

}
