package com.medlife.deliveries.clickpost.data.recommendation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class Result {
    private RecommendationRequest request_details;
    private List<Preference> preference_array;
    private boolean pincode_serviceable;
}
