package com.medlife.deliveries.model.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class GSTInfo {

    @NotBlank
    private String sellerGstin;

    @NotBlank
    private String enterpriseGstin;

    @NotBlank
    private String consigneeGstin;

    @NotBlank
    private String invoiceReference;

    private String ewayBillNumber;

    private boolean sellerRegisteredUnderGst;

    @NotBlank
    private String placeOfSupply;

    @NotBlank
    private String hsnCode;

    @NotNull
    @PositiveOrZero
    private Double gstTaxBase;

    @NotNull
    @PositiveOrZero
    private Double taxableValue;

    @NotNull
    @PositiveOrZero
    private Double gstDiscount;

    @NotNull
    @PositiveOrZero
    private Double gstTotalTax;

    @NotNull
    @PositiveOrZero
    private Double sgstRate;

    @NotNull
    @PositiveOrZero
    private Double cgstRate;

    @NotNull
    @PositiveOrZero
    private Double igstRate;

    @NotNull
    @PositiveOrZero
    private Double sgstAmount;

    @NotNull
    @PositiveOrZero
    private Double cgstAmount;

    @NotNull
    @PositiveOrZero
    private Double igstAmout;

}
