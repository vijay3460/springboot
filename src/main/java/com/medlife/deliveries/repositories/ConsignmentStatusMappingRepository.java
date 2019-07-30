package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.ConsignmentStatusMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsignmentStatusMappingRepository extends JpaRepository<ConsignmentStatusMapping,Long> {

    ConsignmentStatusMapping findOneById(long id);

}
