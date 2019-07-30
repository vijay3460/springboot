package com.medlife.deliveries.repositories;


import com.medlife.deliveries.model.ConsignmentPartnerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsignmentPartnerLogRepository extends JpaRepository<ConsignmentPartnerLog,Long> {

    ConsignmentPartnerLog save(ConsignmentPartnerLog consignmentPartnerLog);

}
