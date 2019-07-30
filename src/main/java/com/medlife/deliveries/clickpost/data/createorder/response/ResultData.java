package com.medlife.deliveries.clickpost.data.createorder.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResultData {

    private String label;

    private String waybill;

    private String referenceNumber;

    private String securityKey;
}
