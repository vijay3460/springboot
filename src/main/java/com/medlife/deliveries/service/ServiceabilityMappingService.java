package com.medlife.deliveries.service;

import com.medlife.deliveries.clickpost.data.recommendation.RecommendationResponse;
import com.medlife.deliveries.clickpost.data.sla.ResultResponse;
import com.medlife.deliveries.model.ClickPostLog;
import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.model.data.ServiceabilitySlaRequest;
import com.medlife.deliveries.model.data.ServiceabilitySlaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface ServiceabilityMappingService {

    List<ServiceabilityMapping> getAllMappings();

    ServiceabilityMapping getMappingById(Long id);

    List<ServiceabilityMapping> insertNewMappings(List<ServiceabilityMapping> serviceabilityMappings);

    ServiceabilityMapping updateMappingById(Long id,ServiceabilityMapping serviceabilityMapping);

    List<ServiceabilityMapping> updateMappings(List<ServiceabilityMapping> serviceabilityMappings);

    void deleteMapping(long id);

    ServiceabilityMapping getServiceabilityMappingByConsignment(Consignment consignment);

    ServiceabilityMapping getMapping(String pickUpPincode, String dropPincode, boolean isRefrigeratedAllowed);

    ServiceabilityMapping updateSlaInMapping(ServiceabilityMapping serviceabilityMapping, ResultResponse resultResponse, ClickPostLog log);

    ServiceabilityMapping updateRecommendedPartner(ServiceabilityMapping mapping, RecommendationResponse recommendationResponse, ClickPostLog log);

    ServiceabilitySlaResponse getServiceabilitySlaResponse(ServiceabilitySlaRequest slaRequest);
}
