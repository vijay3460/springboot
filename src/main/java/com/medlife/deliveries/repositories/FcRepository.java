package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.Fc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcRepository extends JpaRepository<Fc,Long> {

    Fc findOneById(Long id);

    Fc findOneByPincode(String pincode);

}
