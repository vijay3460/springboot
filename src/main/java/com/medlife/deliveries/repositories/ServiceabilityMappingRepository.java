package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.ServiceabilityMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Repository
public interface ServiceabilityMappingRepository extends JpaRepository<ServiceabilityMapping,Long> {

    List<ServiceabilityMapping> findAll();

    ServiceabilityMapping findOneById(long id);

    ServiceabilityMapping save(ServiceabilityMapping serviceabilityMapping);

    ServiceabilityMapping findOneByFcIdAndAreaMappingIdAndIsRefrigeratedAllowed(
            Long originArea, Long areaMappingId, Boolean isRefrigeratedAllowed
    );

    ServiceabilityMapping findOneByFcIdAndAreaMappingId(
            Long originArea, Long areaMappingId
    );

}
