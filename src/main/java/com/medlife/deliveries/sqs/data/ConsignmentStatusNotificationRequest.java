package com.medlife.deliveries.sqs.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsignmentStatusNotificationRequest {

    @JsonProperty("consignment_id")
    private Long consignmentId;

    @JsonProperty("external_reference_id")
    private String externalReferenceID;

    @JsonProperty("status")
    private String status;

}
