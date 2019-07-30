package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentDetails {

    private String order_type;
    private double invoice_value;
    private String invoice_number;
    private String invoice_date;
    private String reference_number;
    private int length;
    private int breadth;
    private int weight;
    private int height;
    private double cod_value;
    private long courier_partner;
    private List<ItemDetail> items;

}
