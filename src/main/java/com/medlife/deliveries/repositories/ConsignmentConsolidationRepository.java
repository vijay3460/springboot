package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.ConsignmentConsolidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
public interface ConsignmentConsolidationRepository extends JpaRepository<ConsignmentConsolidation,Long> {

    List<ConsignmentConsolidation> findAll();

    List<ConsignmentConsolidation> findByStatus(String status);

    ConsignmentConsolidation save(ConsignmentConsolidation consignmentConsolidation);
}
