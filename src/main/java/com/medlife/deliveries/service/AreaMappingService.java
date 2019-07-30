package com.medlife.deliveries.service;

import com.medlife.deliveries.model.AreaMapping;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface AreaMappingService {

    List<AreaMapping> getAllAreaMappings();

    AreaMapping getAreaMappingById(Long id);

    List<AreaMapping> getAreaMappingsByAreaName(String area);

    List<AreaMapping> getAreaMappingsByPincode(String pincode);

    public AreaMapping getAreaMappingByPincode(String pincode);

    public AreaMapping getAreaMappingByPincode(Long pincode);

    List<AreaMapping> insertNewAreaMappings(List<AreaMapping> areaMappings);

    AreaMapping updateAreaMappingById(Long id, AreaMapping areaMapping);

    List<AreaMapping> updateAreaMappings(List<AreaMapping> areaMappings);

    void deleteAreaMapping(long id);
}
