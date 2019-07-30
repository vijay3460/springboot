package com.medlife.deliveries.model.data;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class ValueDetails {

    @NotNull
    @PositiveOrZero
    private Double totalAmount;

    @NotNull
    @Valid
    private GSTInfo gstInfo;

    @NotBlank
    private String paymentType;
}
