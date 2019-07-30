package com.medlife.deliveries.clickpost.data.createorder.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medlife.deliveries.clickpost.enums.DeliveryType;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalInformation {

    private boolean label;
    private ReturnInfo return_info;
    private String awb_number;
    private DeliveryType delivery_type;
    private boolean async;
    private String gst_number;
    private String account_code;
    private String from_wh;
    private String to_wh;
    private String channel_name;

}
