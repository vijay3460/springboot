package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemAdditionalInfo {

    private int length;
    private int height;
    private int breadth;
    private int weight;
    //private String images;
}
