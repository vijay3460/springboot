package com.medlife.deliveries.utils;

import com.medlife.deliveries.clickpost.data.recommendation.RecommendationRequest;
import com.medlife.deliveries.clickpost.data.recommendation.RecommendationResponse;
import com.medlife.deliveries.clickpost.data.sla.SlaOptional;
import com.medlife.deliveries.clickpost.data.sla.SlaRequest;
import com.medlife.deliveries.clickpost.enums.DeliveryType;
import com.medlife.deliveries.clickpost.enums.OrderType;
import com.medlife.deliveries.model.ClickPostLog;
import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.Fc;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.model.data.ServiceabilitySlaRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
public class DataUtils {

    public static List<RecommendationRequest> getClickPostRecommendationRequest(ServiceabilitySlaRequest slaRequest){
        List<RecommendationRequest> requests = new ArrayList<>();
        RecommendationRequest request = new RecommendationRequest();
        request.setReference_number(slaRequest.getExternalReferenceId());
        request.setPickup_pincode(slaRequest.getPickupPincode());
        request.setDrop_pincode(slaRequest.getDropPincode());
        request.setDelivery_type(DeliveryType.FORWARD);
        request.setOrder_type(OrderType.PREPAID.toString());
        request.setInvoice_value(slaRequest.getInvoiceValue());
        request.setItem("Medicines");
        //LBH and W are ignored for while
        requests.add(request);
        return requests;
    }

    public static List<SlaRequest> getClickPostSlaRequest(ServiceabilitySlaRequest slaRequest,String cp_id){
        List<SlaRequest> requests = new ArrayList<>();
        SlaRequest request = new SlaRequest(slaRequest.getPickupPincode(),slaRequest.getDropPincode(),new SlaOptional(cp_id,null));
        requests.add(request);
        return requests;
    }

    public static long getRecommendedPartnerFromClickPostResponse(RecommendationResponse response){
        try{
            return response.getResult().get(0).getPreference_array().get(0).getCp_id();
        }
        catch (Exception e){
            log.info("getRecommendedPartnerFromClickPostResponse : for response : {} , exception is : ",MapperUtils.getJSONString(response),e);
            return -1;
        }
    }

    public static ClickPostLog getClickPostLogFromConsignment(Consignment consignment,ServiceabilityMapping serviceabilityMapping, Fc fc){
        ClickPostLog log = new ClickPostLog();
        log.setPickupPincode(fc.getPincode());
        log.setDropPincode(consignment.getCustomerPincode());
        log.setPartnerId(consignment.getPartnerId());
        log.setServiceabilityId(serviceabilityMapping.getId());
        return log;
    }

    public static ClickPostLog getClickPostLogFromSlaApiRequest(ServiceabilitySlaRequest slaRequest,ServiceabilityMapping mapping,long cp_id){
        ClickPostLog log = new ClickPostLog();
        log.setPickupPincode(slaRequest.getPickupPincode());
        log.setDropPincode(slaRequest.getDropPincode());
        log.setPartnerId(cp_id);
        log.setServiceabilityId(mapping.getId());
        return log;
    }
}
