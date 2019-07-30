package com.medlife.deliveries.service.partner;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.model.data.Partners;
import com.medlife.deliveries.service.partner.clickPost.ClickPostService;
import com.medlife.deliveries.service.ServiceabilityMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
@Resource(name="DeliveryPartnerFactory")
public class DeliveryPartnerFactory {

    @Autowired
    private ClickPostService clickPostService;

    @Autowired
    private ServiceabilityMappingService serviceabilityMappingService;

    public DeliveryPartnerService getServiceablePartner(Consignment consignment){
        ServiceabilityMapping serviceabilityMapping = serviceabilityMappingService.getServiceabilityMappingByConsignment(consignment);

        if (serviceabilityMapping == null){
            log.error("No serviceability mapping found for consignment id: {}", consignment.getId());
            return clickPostService;
        }

        return getServiceablePartnerByMapping(serviceabilityMapping);
    }

    public DeliveryPartnerService getServiceablePartnerByMapping(ServiceabilityMapping serviceabilityMapping){
        Long partnerId =  serviceabilityMapping.getDeliveryPartners().get(0).getId();

        if (partnerId == Partners.CLICKPOST.id()){
            return clickPostService;
        } else {
            throw new UnsupportedClassVersionError();
        }
    }

}
