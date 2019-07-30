package com.medlife.deliveries.clickpost.data;

import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class MetaResponse {
    private String status;
    private boolean success;
    private String message;
}
