package com.medlife.deliveries.service;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.service.ApiRequestValidations.ValidationService;
import com.medlife.deliveries.service.partner.DeliveryPartnerFactory;
import com.medlife.deliveries.service.partner.DeliveryPartnerService;
import com.medlife.deliveries.repositories.AreaMappingRepository;
import com.medlife.deliveries.repositories.ConsignmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
@Service
public class ConsignmentServiceImpl implements ConsignmentService {

    @Autowired
    private ConsignmentRepository consignmentRepository;

    @Autowired
    private DeliveryPartnerFactory  deliveryPartnerFactory;

    @Autowired
    private ServiceabilityMappingService serviceabilityMappingService;

    @Autowired
    private AreaMappingRepository areaMappingRepository;

    @Autowired
    private ValidationService<Consignment,Consignment> validationService;

    @Override
    public List<Consignment> getAllConsignments() {
        return consignmentRepository.findAll();
    }

    @Override
    public Consignment getConsignmentById(Long id) {
        return consignmentRepository.findOneById(id);
    }

    @Override
    public List<Consignment> insertNewConsignments(List<Consignment> consignments) {
        List<Consignment> newConsignments = new ArrayList<>();

        for(Consignment consignment: consignments){
            validationService.validateApiRequest(consignment);
            consignment = validationService.getEntityFromRequest(consignment);
            consignmentRepository.save(consignment);
            double currentTime = System.currentTimeMillis();
            Consignment bookedConsignment = book(consignment);
            Consignment updatedSlaConsignment = updateSla(bookedConsignment);
            newConsignments.add(updatedSlaConsignment);
            double updatedTime = System.currentTimeMillis() - currentTime;
            log.info("Time taken to complete booking is : {} sec",updatedTime/1000);
        }
        return newConsignments;
    }

    @Override
    public Consignment updateConsignmentById(Long id, Consignment consignment) {
        consignment.setId(id);
        return updateConsignments(Arrays.asList(consignment)).get(0);
    }

    @Override
    public List<Consignment> updateConsignments(List<Consignment> consignments) {
        List<Consignment> updatedConsignments = new ArrayList<>();

        //put validations here
        //made updated Object here by fetching the old one, and then replacing the new Values.

        for(Consignment consignment : consignments){
            Consignment updatedConsignment = consignmentRepository.save(consignment);
            updatedConsignments.add(updatedConsignment);
        }

        return updatedConsignments;
    }

    private Consignment updateSla(Consignment consignment){
        DeliveryPartnerService deliveryPartnerService =  deliveryPartnerFactory.getServiceablePartner(consignment);
        ServiceabilityMapping serviceabilityMapping = serviceabilityMappingService.getServiceabilityMappingByConsignment(consignment);
        return deliveryPartnerService.updateSla(consignment,serviceabilityMapping);
    }

    private Consignment book(Consignment consignment) {
        ServiceabilityMapping serviceabilityMapping = serviceabilityMappingService.getServiceabilityMappingByConsignment(consignment);
        if(serviceabilityMapping==null)
        {
            log.info("ServiceabilityMapping not found for consignment : {}",consignment.getId());
            return null;
        }
        DeliveryPartnerService deliveryPartnerService = deliveryPartnerFactory.getServiceablePartner(consignment);
        return deliveryPartnerService.book(consignment, serviceabilityMapping);
    }

}
