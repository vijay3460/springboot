package com.medlife.deliveries.clickpost.enums;

import java.util.HashMap;
import java.util.Map;

public enum TrackingStatusCode {

    ORDER_PLACED(1,"Order Has Been Placed / Manifested on Courier Partner"),
    PICKUP_PENDING(2,"Pickup Pending"),
    PICKUP_CANCELLED(3,	"Pickup Cancelled"),
    PICKED_UP(4, "Pickup Has Been Done"),
    IN_TRANSIT (5, "In Transit"),
    OUT_FOR_DELIVERY(6, "Shipment Out For Delivery"),
    NOT_SERVICEABLE(7,"Area For Delivery Is Not Servicable"),
    DELIVERED(8, "Shipment Delivered"),
    FAILED_DELIVERY(9, "Delivery Failed"),
    CANCELLED_ORDER(10,	"Order Has Been Cancelled"),
    RTO_REQUESTED(11, "Rto For Shipment has been Requested"),
    RTO(12, "Marked As Return"),
    RTO_OUT_FOR_DELIVERY(13, "Shipment Is Out For Delivery For RTO"),
    RTO_DELIVERED(14, "RTO Delivered"),
    RTO_FAILED(15,	"RTO Failed"),
    LOST(16,"Shipment is Lost"),
    DAMAGED(17,"Shipment is damaged"),
    SHIPMENT_DELAYED(18,"Shipment Is Delayed Or Misroute"),
    CONTACT_CUSTOMER_CARE(19,"Contact To The Customer Care"),
    SHIPMENT_HELD(20,"Shipment Is being held"),
    RTO_IN_TRANSIT(21,"RTO In Transit"),
    OUT_FOR_PICKUP(25, "Shipment Out For Pickup"),
    RTO_CONTACT_CUSTOMER_CARE(26, "RTO Contact Customer Care"),
    RTO_SHIPMENT_DELAY(27, "RTO Shipment Delayed"),
    AWB_REGISTERED(28, "AWB registered on Clickpost"),
    RETURN_ORDER_PLACED(101, "Order Placed in Clickpost for reverse pickup by Customer");

    private Integer statusCode;
    private String description;

    TrackingStatusCode(Integer statusCode, String description){
        this.statusCode = statusCode;
        this.description = description;
    }


    public Integer statusCode(){
        return this.statusCode;
    }

    public String desciption(){
        return this.description;
    }

    private static final Map<Integer, TrackingStatusCode> lookup = new HashMap<>();


    static {
        for (TrackingStatusCode t : TrackingStatusCode.values()) {
            lookup.put(t.statusCode(), t);
        }
    }

    public static TrackingStatusCode get(Integer statusCode) {
        return lookup.get(statusCode);
    }

}
