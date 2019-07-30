package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderRequest {
    private PickupInfo pickup_info;
    private DropInfo drop_info;
    private ShipmentDetails shipment_details;
    private AdditionalInformation additional;
}
