package com.medlife.deliveries.service.partner;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ServiceabilityMapping;
import org.springframework.stereotype.Service;

@Service
public interface DeliveryPartnerService{

    Consignment book(Consignment consignment, ServiceabilityMapping serviceabilityMapping);

    Boolean cancel(Consignment consignment);

    Consignment updateSla(Consignment consignment,ServiceabilityMapping serviceabilityMapping);

}
