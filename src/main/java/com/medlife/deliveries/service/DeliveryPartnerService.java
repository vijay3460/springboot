package com.medlife.deliveries.service;

import com.medlife.deliveries.model.DeliveryPartner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface DeliveryPartnerService {

    List<DeliveryPartner> getAllDeliveryPartners();

    DeliveryPartner getDeliveryPartnerById(Long id);

    DeliveryPartner getDeliveryPartnerByName(String name);

    List<DeliveryPartner> insertDeliveryPartners(List<DeliveryPartner> deliveryPartners);

    DeliveryPartner updateDeliveryPartnerById(Long id,DeliveryPartner deliveryPartner);

    List<DeliveryPartner> updateDeliveryPartners(List<DeliveryPartner> deliveryPartners);

    void deleteDeliveryPartner(long id);

}
