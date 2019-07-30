package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ConsignmentConsolidation;
import com.medlife.deliveries.model.ConsignmentStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
public interface ConsignmentStatusLogRepository extends JpaRepository<ConsignmentStatusLog,Long> {

    List<ConsignmentStatusLog> findAll();

    ConsignmentConsolidation save(ConsignmentConsolidation save);

    List<ConsignmentStatusLog> findByConsignmentIdAndStatus(
            Long consignmentId, String statuss);
}
