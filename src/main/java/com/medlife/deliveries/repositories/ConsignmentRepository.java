package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment,Long> {

    List<Consignment> findAll();

    Consignment findOneById(long id);

    Consignment save(Consignment consignment);

    Consignment findOneByAwbAndDeliveryType(String awb, String  deliveryType);
}