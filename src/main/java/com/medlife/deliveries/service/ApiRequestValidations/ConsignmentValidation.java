package com.medlife.deliveries.service.ApiRequestValidations;

import com.medlife.deliveries.exceptions.ApiValidationException;
import com.medlife.deliveries.exceptions.FieldValidations;
import com.medlife.deliveries.model.Consignment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
@Service
public class ConsignmentValidation implements ValidationService<Consignment,Consignment> {
    @Override
    public void validateApiRequest(Consignment apiRequestData) throws ApiValidationException,ConstraintViolationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Consignment>> violations = validator.validate(apiRequestData);
        if(violations.size()>0)
        {
            throw new ConstraintViolationException(violations);
        }
    }

    @Override
    public Consignment getEntityFromRequest(Consignment apiRequestData) {
        apiRequestData.setStatus("WAITING_FOR_DELIVERY");
        apiRequestData.setCustomerCountry("INDIA");
        apiRequestData.setDeliveryType("outbound");
        apiRequestData.setIsPrepaid((apiRequestData.getValueDetails().getPaymentType().equalsIgnoreCase("PREPAID")));
        return apiRequestData;
    }
}
