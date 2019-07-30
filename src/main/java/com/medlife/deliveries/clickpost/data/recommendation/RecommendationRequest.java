package com.medlife.deliveries.clickpost.data.recommendation;

import com.medlife.deliveries.clickpost.enums.DeliveryType;
import lombok.Data;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class RecommendationRequest {
    private String pickup_pincode;
    private String drop_pincode;
    private String order_type;
    private String reference_number;
    private String item;
    private double invoice_value;
    private DeliveryType delivery_type;
    private int weight;
    private int height;
    private int breadth;
    private int length;
}
