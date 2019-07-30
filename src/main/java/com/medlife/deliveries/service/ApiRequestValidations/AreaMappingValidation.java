package com.medlife.deliveries.service.ApiRequestValidations;

import com.medlife.deliveries.exceptions.ApiValidationException;
import com.medlife.deliveries.model.AreaMapping;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public class AreaMappingValidation implements ValidationService<AreaMapping,AreaMapping> {

    @Override
    public void validateApiRequest(AreaMapping apiRequestData) throws ApiValidationException,ConstraintViolationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<AreaMapping>> violations = validator.validate(apiRequestData);
        if(violations.size()>0)
        {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    public AreaMapping getEntityFromRequest(AreaMapping apiRequestData) {
        //pre process
        return apiRequestData;
    }
}
