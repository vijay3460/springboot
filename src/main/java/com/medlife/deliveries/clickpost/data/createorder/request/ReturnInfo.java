package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnInfo {

    private String pincode;
    private String address;
    private String state;
    private String phone;
    private String name;
    private String city;
    private String country;
}
