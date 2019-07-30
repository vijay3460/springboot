package com.medlife.deliveries.service;

import com.medlife.deliveries.model.DeliveryPartner;
import com.medlife.deliveries.repositories.DeliveryPartnerRepository;
import com.medlife.deliveries.service.ApiRequestValidations.ValidationService;
import com.medlife.deliveries.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    private ValidationService<DeliveryPartner,DeliveryPartner> deliveryPartnerValidation;

    @Override
    public List<DeliveryPartner> getAllDeliveryPartners() {
        return deliveryPartnerRepository.findAll();
    }

    @Override
    public DeliveryPartner getDeliveryPartnerById(Long id) {
        return deliveryPartnerRepository.findOneById(id);
    }

    @Override
    public DeliveryPartner getDeliveryPartnerByName(String name) {
        return deliveryPartnerRepository.findOneByName(name);
    }

    @Override
    public List<DeliveryPartner> insertDeliveryPartners(List<DeliveryPartner> deliveryPartners) {
        List<DeliveryPartner> savedDeliveryPartners = new ArrayList<>();
        for(DeliveryPartner deliveryPartner : deliveryPartners){
            deliveryPartnerValidation.validateApiRequest(deliveryPartner);
            deliveryPartner = deliveryPartnerValidation.getEntityFromRequest(deliveryPartner);
            DeliveryPartner savedDeliveryPartner = deliveryPartnerRepository.save(deliveryPartner);
            savedDeliveryPartners.add(savedDeliveryPartner);
        }
        return savedDeliveryPartners;
    }

    @Override
    public DeliveryPartner updateDeliveryPartnerById(Long id, DeliveryPartner deliveryPartner) {
        deliveryPartner.setId(id);
        return updateDeliveryPartners(Arrays.asList(deliveryPartner)).get(0);
    }

    @Override
    public List<DeliveryPartner> updateDeliveryPartners(List<DeliveryPartner> deliveryPartners) {
        List<DeliveryPartner> updatedDeliveryPartners = new ArrayList<>();
        //check for validations here.
        for(DeliveryPartner deliveryPartner : deliveryPartners){
            DeliveryPartner updatedDeliveryPartner = deliveryPartnerRepository.save(deliveryPartner);
            updatedDeliveryPartners.add(updatedDeliveryPartner);
        }
        return updatedDeliveryPartners;
    }

    @Override
    public void deleteDeliveryPartner(long id) {
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findOneById(id);
        //check for validations
        deliveryPartner.setIsActive(false);
        deliveryPartner.setDeletedAt(DateUtils.getCurrentTimestampInSeconds());
        deliveryPartnerRepository.save(deliveryPartner);
    }


}
