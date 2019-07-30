package com.medlife.deliveries.service.ApiRequestValidations;

import com.medlife.deliveries.exceptions.ApiValidationException;
import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ConsignmentSubscription;
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
public class ConsignmentSubscriptionValidation implements ValidationService<ConsignmentSubscription,ConsignmentSubscription> {
    @Override
    public void validateApiRequest(ConsignmentSubscription apiRequestData) throws ApiValidationException, ConstraintViolationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ConsignmentSubscription>> violations = validator.validate(apiRequestData);
        if(violations.size()>0)
        {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    public ConsignmentSubscription getEntityFromRequest(ConsignmentSubscription apiRequestData) {
        return apiRequestData;
    }
}
