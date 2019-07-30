package com.medlife.deliveries.repositories;

import com.medlife.deliveries.model.ClickPostLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Harshit Singh Chauhan
 */
public interface ClickPostLogRepository extends JpaRepository<ClickPostLog,Long> {

    ClickPostLog save(ClickPostLog log);

}
