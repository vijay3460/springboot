package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.AreaMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.geom.Area;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
public interface AreaMappingRepository extends JpaRepository<AreaMapping,Long> {

    List<AreaMapping> findAll();

    AreaMapping findOneById(long id);

    List<AreaMapping> findByPincode(Long pincode);

    AreaMapping findOneByPincode(Long pincode);

    AreaMapping findFirstByPincode(Long pincode);

    AreaMapping findOneByArea(String area);

    List<AreaMapping> findByArea(String area);

    AreaMapping save(AreaMapping areaMapping);


}
