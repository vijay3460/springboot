package com.medlife.deliveries.service;

import com.medlife.deliveries.clickpost.data.recommendation.RecommendationResponse;
import com.medlife.deliveries.clickpost.data.sla.ResultResponse;
import com.medlife.deliveries.clickpost.data.sla.SlaResponse;
import com.medlife.deliveries.model.AreaMapping;
import com.medlife.deliveries.model.ClickPostLog;
import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.model.data.*;
import com.medlife.deliveries.repositories.ClickPostLogRepository;
import com.medlife.deliveries.repositories.FcRepository;
import com.medlife.deliveries.repositories.ServiceabilityMappingRepository;
import com.medlife.deliveries.service.ApiRequestValidations.ValidationService;
import com.medlife.deliveries.service.partner.clickPost.ClickPostRestClient;
import com.medlife.deliveries.utils.DataUtils;
import com.medlife.deliveries.utils.DateUtils;
import com.medlife.deliveries.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
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
public class ServiceabilityMappingServiceImpl implements ServiceabilityMappingService {

    @Autowired
    private ServiceabilityMappingRepository serviceabilityMappingRepository;

    @Autowired
    private AreaMappingService areaMappingService;

    @Autowired
    private FcRepository fcRepository;

    @Autowired
    private ClickPostRestClient clickPostRestClient;

    @Autowired
    private ClickPostLogRepository clickPostLogRepository;

    @Autowired
    private ValidationService<ServiceabilityMapping,ServiceabilityMapping> mappingValidationService;
    @Override
    public List<ServiceabilityMapping> getAllMappings() {
        return serviceabilityMappingRepository.findAll();
    }

    @Override
    public ServiceabilityMapping getMappingById(Long id) {
        return serviceabilityMappingRepository.findOneById(id);
    }

    @Override
    public List<ServiceabilityMapping> insertNewMappings(List<ServiceabilityMapping> serviceabilityMappings) {
        List<ServiceabilityMapping> newMappings = new ArrayList<>();
        for(ServiceabilityMapping serviceabilityMapping : serviceabilityMappings){
            mappingValidationService.validateApiRequest(serviceabilityMapping);
            serviceabilityMapping = mappingValidationService.getEntityFromRequest(serviceabilityMapping);
            ServiceabilityMapping newMapping = serviceabilityMappingRepository.save(serviceabilityMapping);
            newMappings.add(newMapping);
        }
        return newMappings;
    }

    @Override
    public ServiceabilityMapping updateMappingById(Long id, ServiceabilityMapping serviceabilityMapping) {
        serviceabilityMapping.setId(id);
        return updateMappings(Arrays.asList(serviceabilityMapping)).get(0);
    }

    @Override
    public List<ServiceabilityMapping> updateMappings(List<ServiceabilityMapping> serviceabilityMappings) {
        List<ServiceabilityMapping> updatedMappings = new ArrayList<>();
        //check for validations here.
        //Preprocess
        for(ServiceabilityMapping serviceabilityMapping : serviceabilityMappings){
            ServiceabilityMapping updatedMapping = serviceabilityMappingRepository.save(serviceabilityMapping);
            updatedMappings.add(updatedMapping);
        }
        return updatedMappings;
    }

    @Override
    public void deleteMapping(long id) {
        ServiceabilityMapping mapping = serviceabilityMappingRepository.findOneById(id);
        mapping.setDeletedAt(DateUtils.getCurrentTimestampInSeconds());
        serviceabilityMappingRepository.save(mapping);
    }

    public ServiceabilityMapping getServiceabilityMappingByConsignment(Consignment consignment) {
        Long fcId = consignment.getFcId();
        String pincode  = consignment.getCustomerPincode();
        Boolean isRefrigerated = consignment.getIsRefrigerated();
        AreaMapping areaMapping = areaMappingService.getAreaMappingByPincode(pincode);
        Long areaMappingId = areaMapping.getId();
        return getMapping(fcId,areaMappingId,isRefrigerated);
    }

    @Override
    public ServiceabilityMapping getMapping(String pickUpPincode, String dropPincode, boolean isRefrigeratedAllowed) {
        Long fcId = fcRepository.findOneByPincode(pickUpPincode).getId();
        Long areaMappingId = areaMappingService.getAreaMappingByPincode(dropPincode).getId();
        return getMapping(fcId,areaMappingId,isRefrigeratedAllowed);
    }

    @Override
    public ServiceabilityMapping updateSlaInMapping(ServiceabilityMapping mapping, ResultResponse resultResponse, ClickPostLog log) {
        //add log updates here.
        List<PartnerDetails> deliveryPartners = new ArrayList<>();
        PartnerDetails newPartner = mapping.getDeliveryPartners().get(0);
        newPartner.setPredicted_sla_min(resultResponse.getPredicted_sla_min());
        newPartner.setPredicted_sla_max(resultResponse.getPredicted_sla_max());
        deliveryPartners.add(newPartner);
        mapping.setDeliveryPartners(deliveryPartners);

        log.setRequestType(BasicConstants.CLICKPOST_SLA_REQUEST);
        log.setDetails(MapperUtils.getJSONString(resultResponse));
        clickPostLogRepository.save(log);
        return updateMappingById(mapping.getId(),mapping);
    }

    public ServiceabilityMapping updateRecommendedPartner(ServiceabilityMapping mapping, RecommendationResponse recommendationResponse, ClickPostLog log){
        long recommendedPartner = DataUtils.getRecommendedPartnerFromClickPostResponse(recommendationResponse);
        if(recommendedPartner == -1)
            return mapping;
        List<PartnerDetails> deliveryPartners = new ArrayList<>();
        PartnerDetails newPartner = mapping.getDeliveryPartners().get(0);
        newPartner.setSubpartnerId(recommendedPartner);
        deliveryPartners.add(newPartner);
        mapping.setDeliveryPartners(deliveryPartners);

        log.setRequestType(BasicConstants.CLICKPOST_LOG_RECOMMENDATION);
        log.setDetails(MapperUtils.getJSONString(recommendationResponse.getResult()));
        clickPostLogRepository.save(log);

        return updateMappingById(mapping.getId(),mapping);
    }

    @Override
    public ServiceabilitySlaResponse getServiceabilitySlaResponse(ServiceabilitySlaRequest slaRequest) {
        ServiceabilitySlaResponse slaResponse =  new ServiceabilitySlaResponse();
        slaResponse.setServiceableAddress(false);
        try{
            ServiceabilityMapping mapping = getMapping(slaRequest.getPickupPincode(),slaRequest.getDropPincode(),slaRequest.isRefrigerated());
            if(mapping != null){
                PartnerDetails subPartnerDetails = mapping.getDeliveryPartners().get(0);
                if(subPartnerDetails!=null && subPartnerDetails.getId()!=null){
                    if(subPartnerDetails.getSubpartnerId()==null){
                        if(subPartnerDetails.getId() == Partners.CLICKPOST.id()){
                            RecommendationResponse recommendationResponse = clickPostRestClient.getRecommendation(DataUtils.getClickPostRecommendationRequest(slaRequest));
                            ClickPostLog log = DataUtils.getClickPostLogFromSlaApiRequest(slaRequest,mapping,subPartnerDetails.getId());
                            mapping = updateRecommendedPartner(mapping,recommendationResponse,log);
                            subPartnerDetails = mapping.getDeliveryPartners().get(0);
                        }
                    }
                    if(subPartnerDetails.getPredicted_sla_max()==null){
                        if(subPartnerDetails.getId() == Partners.CLICKPOST.id() && subPartnerDetails.getSubpartnerId()!=-1){
                            SlaResponse clickPostSlaResponse = clickPostRestClient.getSla(DataUtils.getClickPostSlaRequest(slaRequest,subPartnerDetails.getSubpartnerId().toString()));
                            ClickPostLog log = DataUtils.getClickPostLogFromSlaApiRequest(slaRequest,mapping,subPartnerDetails.getId());
                            mapping = updateSlaInMapping(mapping,clickPostSlaResponse.getResult().get(0),log);
                            subPartnerDetails = mapping.getDeliveryPartners().get(0);
                        }
                    }

                    //after setting partner and sla in mapping, we should get Data in case of ClickPost
                    slaResponse.setPredictedSlaMin(subPartnerDetails.getPredicted_sla_min());
                    slaResponse.setPredictedSlaMax(subPartnerDetails.getPredicted_sla_max());
                    slaResponse.setServiceableAddress(true);
                }
            }
        }
        catch (Exception e){
            log.error("getServiceabilitySlaResponse : Exception occured for slaRequest : {} - ",MapperUtils.getJSONString(slaRequest),e);
        }
        return slaResponse;
    }

    private ServiceabilityMapping getMapping(Long fcId, Long areaMappingId, Boolean isRefrigerated){
        if(isRefrigerated){
            return serviceabilityMappingRepository.findOneByFcIdAndAreaMappingIdAndIsRefrigeratedAllowed(fcId, areaMappingId, isRefrigerated);
        }
        else{
            return serviceabilityMappingRepository.findOneByFcIdAndAreaMappingId(fcId, areaMappingId);
        }
    }
}
