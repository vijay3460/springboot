package com.medlife.deliveries.clickpost.data.recommendation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class Preference {
    private String cp_name;
    private int cp_id;
    private String account_code;
    private int priority;
}
