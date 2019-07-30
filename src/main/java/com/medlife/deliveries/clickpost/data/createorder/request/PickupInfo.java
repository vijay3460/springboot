package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PickupInfo {

    private String pickup_state;
    private String pickup_address;
    private String email;
    private String pickup_time;
    private String pickup_pincode;
    private String pickup_city;
    private String tin;
    private String pickup_name;
    private String pickup_phone;
    private String pickup_country;

}
