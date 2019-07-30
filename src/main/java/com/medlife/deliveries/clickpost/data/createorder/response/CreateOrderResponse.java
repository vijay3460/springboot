package com.medlife.deliveries.clickpost.data.createorder.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CreateOrderResponse {

    @JsonProperty("tracking_id")
    private Long trackingId;

    @JsonProperty("order_id")
    private Long orderID;

    @JsonProperty("meta")
    private MetaData metaData;

    @JsonProperty("result")
    private ResultData resultData;

}
