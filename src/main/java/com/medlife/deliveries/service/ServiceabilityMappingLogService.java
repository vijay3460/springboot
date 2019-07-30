package com.medlife.deliveries.service;

import com.medlife.deliveries.model.ServiceabilityMappingLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */

@Service
public interface ServiceabilityMappingLogService {

    List<ServiceabilityMappingLog> getAllLogs();

    List<ServiceabilityMappingLog> insertNewLogs(List<ServiceabilityMappingLog> serviceabilityMappingLogs);

    List<ServiceabilityMappingLog> updateLogs(List<ServiceabilityMappingLog> serviceabilityMappingLogs);

}
