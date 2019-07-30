package com.medlife.deliveries.clickpost.data.createorder.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MetaData {

    private String message;

    private Integer status;

    private Boolean success;

}
