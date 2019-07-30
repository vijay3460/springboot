package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.ServiceabilityMappingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Repository
public interface ServiceabilityMappingLogRepository extends JpaRepository<ServiceabilityMappingLog,Long> {

    List<ServiceabilityMappingLog> findAll();

    ServiceabilityMappingLog save(ServiceabilityMappingLog serviceabilityMappingLog);
}
