package com.medlife.deliveries.clickpost.data.sla;

import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class SlaOptional {
    private String cp_id;
    private String awb;

    public SlaOptional(){}

    public SlaOptional(String cpId,String awb){
        this.cp_id = cpId;
        this.awb = awb;
    }
}


