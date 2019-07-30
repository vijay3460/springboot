package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ConsignmentSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
public interface ConsignmentSubscriptionRepository extends JpaRepository<ConsignmentSubscription,Long> {

    List<ConsignmentSubscription> findAll();

    List<ConsignmentSubscription> findAllByConsignmentId(long consignmentId);

    ConsignmentSubscription findOneById(long id);

    ConsignmentSubscription findOneByConsignmentId(long consignmentId);

    ConsignmentSubscription save(Consignment consignment);

}
