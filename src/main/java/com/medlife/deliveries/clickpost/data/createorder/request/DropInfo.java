package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DropInfo {

    private String drop_address;
    private String drop_phone;
    private String drop_country;
    private String drop_state;
    private String drop_pincode;
    private String drop_city;
    private String drop_name;
}
