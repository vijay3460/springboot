package com.medlife.deliveries.clickpost.data.recommendation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medlife.deliveries.clickpost.data.MetaResponse;
import lombok.Data;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class RecommendationResponse {
    private MetaResponse meta;
    private List<Result> result;
}
