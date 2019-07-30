package com.medlife.deliveries.model.enums;

/**
 * @author : Harshit Singh Chauhan
 */
public enum DeliveryType {

    INBOUND("inbound"),OUTBOUND("outbound");

    private String value;

    DeliveryType(String value){
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}

