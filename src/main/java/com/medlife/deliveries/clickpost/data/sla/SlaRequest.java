package com.medlife.deliveries.clickpost.data.sla;

import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class SlaRequest {
    private String pickup_pincode;
    private String drop_pincode;
    private SlaOptional optional;

    public SlaRequest(){

    }

    public SlaRequest(String pickupPinCode,String dropPinCode,SlaOptional optional){
        this.pickup_pincode=pickupPinCode;
        this.drop_pincode=dropPinCode;
        this.optional=optional;
    }
}
