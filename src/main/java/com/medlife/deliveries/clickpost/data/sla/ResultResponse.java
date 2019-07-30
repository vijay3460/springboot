package com.medlife.deliveries.clickpost.data.sla;

import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class ResultResponse {
    private int predicted_sla_min;
    private int min_sla_cp_id;
    private int predicted_sla_max;
}
