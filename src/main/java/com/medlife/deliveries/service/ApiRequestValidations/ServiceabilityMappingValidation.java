package com.medlife.deliveries.service.ApiRequestValidations;

import com.medlife.deliveries.exceptions.ApiValidationException;

import com.medlife.deliveries.model.ServiceabilityMapping;
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
public class ServiceabilityMappingValidation implements ValidationService<ServiceabilityMapping,ServiceabilityMapping> {
    @Override
    public void validateApiRequest(ServiceabilityMapping apiRequestData) throws ApiValidationException,ConstraintViolationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ServiceabilityMapping>> violations = validator.validate(apiRequestData);
        if(violations.size()>0)
        {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    public ServiceabilityMapping getEntityFromRequest(ServiceabilityMapping apiRequestData) {
        // pre process data
        return apiRequestData;
    }
}
