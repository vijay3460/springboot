package com.medlife.deliveries.sqs.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsignmentStatus {

    @JsonProperty("delivery_partner_id")
    private Long deliveryPartnerId;

    @JsonProperty("consignment_status_mapping_id")
    private Long consignmentStatusMappingId;

    @JsonProperty("received_at")
    private Long receivedAt;

    @JsonProperty("processed_at")
    private Long processedAt;

    @JsonProperty("awb")
    private String awb;

    @JsonProperty("delivery_partner_payload")
    private JsonNode deliveryPartnerPayload;

}
