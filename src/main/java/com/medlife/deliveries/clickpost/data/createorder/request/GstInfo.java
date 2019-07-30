package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GstInfo {

    private String seller_gstin;
    private double taxable_value;
    private String ewaybill_serial_number;
    private boolean is_seller_registered_under_gst;
    private int sgst_tax_rate;
    private String place_of_supply;
    private double gst_discount;
    private String hsn_code;
    private double sgst_amount;
    private String enterprise_gstin;
    private double gst_total_tax;
    private double igst_amount;
    private double cgst_amount;
    private double gst_tax_base;
    private String consignee_gstin;
    private int igst_tax_rate;
    private String invoice_reference;
    private int cgst_tax_rate;

    public void setIs_seller_registered_under_gst(boolean is_seller_registered_under_gst) {
        this.is_seller_registered_under_gst = is_seller_registered_under_gst;
    }

    public boolean getIs_seller_registered_under_gst() {
        return is_seller_registered_under_gst;
    }
}
