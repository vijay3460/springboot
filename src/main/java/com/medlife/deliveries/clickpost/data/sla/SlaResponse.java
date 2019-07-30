package com.medlife.deliveries.clickpost.data.sla;

import com.medlife.deliveries.clickpost.data.MetaResponse;
import lombok.Data;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Data
public class SlaResponse {
    private MetaResponse meta;
    private List<ResultResponse> result;
}
