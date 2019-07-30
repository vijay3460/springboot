package com.medlife.deliveries.model.data;

import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class Customer {
    private String name;
    private String address;
    private String city;
    private String state;
    private int pincode;
    private String country;
    private long phone;
}
