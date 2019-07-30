package com.medlife.deliveries.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDetails {

    @NotNull
    private Long id;

    private Long subpartnerId;
    private Integer predicted_sla_min;
    private Integer predicted_sla_max;
    private Long dailyLimit; // not getting used in v1
    private Long monthlyLimit; // not getting used in v1

}
