package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long> {

    List<DeliveryPartner> findAll();

    DeliveryPartner findOneById(long id);

    DeliveryPartner findOneByName(String name);

    DeliveryPartner save(DeliveryPartner partner);

    List<DeliveryPartner> findByIdIn(List<Long> ids);
}

