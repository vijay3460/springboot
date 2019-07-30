package com.medlife.deliveries.service;

import com.medlife.deliveries.model.AreaMapping;
import com.medlife.deliveries.repositories.AreaMappingRepository;
import com.medlife.deliveries.service.ApiRequestValidations.ValidationService;
import com.medlife.deliveries.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public class AreaMappingServiceImpl implements AreaMappingService{

    @Autowired
    private AreaMappingRepository areaMappingRepository;

    @Autowired
    private ValidationService<AreaMapping,AreaMapping> areaMappingValidationService;

    @Override
    public List<AreaMapping> getAllAreaMappings() {
        return areaMappingRepository.findAll();
    }

    @Override
    public AreaMapping getAreaMappingById(Long id) {
        return areaMappingRepository.findOneById(id);
    }

    @Override
    public List<AreaMapping> getAreaMappingsByAreaName(String area) {
        return areaMappingRepository.findByArea(area);
    }

    @Override
    public List<AreaMapping> getAreaMappingsByPincode(String pincode) {
        return areaMappingRepository.findByPincode(Long.parseLong(pincode));
    }

    public AreaMapping getAreaMappingByPincode(String pincode){
        return areaMappingRepository.findFirstByPincode(Long.parseLong(pincode));
    }

    public AreaMapping getAreaMappingByPincode(Long pincode){
        return areaMappingRepository.findOneByPincode(pincode);
    }

    @Override
    public List<AreaMapping> insertNewAreaMappings(List<AreaMapping> areaMappings) {
        List<AreaMapping> newAreaMappings = new ArrayList<>();
        for(AreaMapping areaMapping : areaMappings){
            areaMappingValidationService.validateApiRequest(areaMapping);
            areaMapping = areaMappingValidationService.getEntityFromRequest(areaMapping);
            AreaMapping newAreaMapping = areaMappingRepository.save(areaMapping);
            newAreaMappings.add(newAreaMapping);
        }
        return newAreaMappings;
    }

    @Override
    public AreaMapping updateAreaMappingById(Long id, AreaMapping areaMapping) {
        areaMapping.setId(id);
        return updateAreaMappings(Arrays.asList(areaMapping)).get(0);
    }

    @Override
    public List<AreaMapping> updateAreaMappings(List<AreaMapping> areaMappings) {
        List<AreaMapping> updatedAreaMappings = new ArrayList<>();
        //put validations here.

        for(AreaMapping areaMapping : areaMappings){
            AreaMapping newAreaMapping = areaMappingRepository.save(areaMapping);
            updatedAreaMappings.add(newAreaMapping);
        }
        return updatedAreaMappings;
    }

    @Override
    public void deleteAreaMapping(long id) {
        AreaMapping areaMapping = areaMappingRepository.findOneById(id);
        areaMapping.setDeletedAt(DateUtils.getCurrentTimestampInSeconds());
        areaMappingRepository.save(areaMapping);
    }
}
