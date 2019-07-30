package com.medlife.deliveries.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class ServiceabilitySlaResponse {
    @JsonProperty("predicted_sla_min")
    private Integer predictedSlaMin;

    @JsonProperty("predicted_sla_max")
    private Integer predictedSlaMax;

    @JsonProperty("is_serviceable_address")
    private boolean serviceableAddress;

    @JsonProperty("extra_info")
    private String extraInfo;

}