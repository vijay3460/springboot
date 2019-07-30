package com.medlife.deliveries.service.ApiRequestValidations;

import com.medlife.deliveries.exceptions.ApiValidationException;
import com.medlife.deliveries.model.DeliveryPartner;
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
public class DeliveryPartnerValidation implements ValidationService<DeliveryPartner,DeliveryPartner> {
    @Override
    public void validateApiRequest(DeliveryPartner apiRequestData) throws ApiValidationException,ConstraintViolationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DeliveryPartner>> violations = validator.validate(apiRequestData);
        if(violations.size()>0)
        {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    public DeliveryPartner getEntityFromRequest(DeliveryPartner apiRequestData) {
        //pre Process Data Here.
        return apiRequestData;
    }
}
