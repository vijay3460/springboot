package com.medlife.deliveries.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class ServiceabilitySlaRequest {
    @JsonProperty("pickup_pincode")
    private String pickupPincode;

    @JsonProperty("drop_pincode")
    private String dropPincode;


    @JsonProperty("is_refrigerated")
    private boolean refrigerated;

    @JsonProperty("mode")
    private TransportMode mode;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("external_reference_id")
    private String externalReferenceId;

    @JsonProperty("invoice_value")
    private double invoiceValue;
}
