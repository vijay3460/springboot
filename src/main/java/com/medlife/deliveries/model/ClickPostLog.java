package com.medlife.deliveries.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@Entity
@Table(name = "clickpost_logs")
public class ClickPostLog extends BaseModel{

    @Column(name = "pickup_pincode")
    private String pickupPincode;

    @Column(name = "drop_pincode")
    private String dropPincode;

    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "serviceability_id")
    private Long serviceabilityId;

    @Column(name = "request_type")
    private String requestType;

    @Column(name="details")
    private String details;

}
