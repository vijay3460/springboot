package com.medlife.deliveries.service;

import com.medlife.deliveries.model.ConsignmentStatusLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */

@Service
public interface ConsignmentStatusLogService {

    List<ConsignmentStatusLog> getAllStatusLogs();

    List<ConsignmentStatusLog> insertNewStatusLogs(List<ConsignmentStatusLog> statusLogs);

    List<ConsignmentStatusLog> updateStatusLogs(List<ConsignmentStatusLog> statusLogs);

    void deleteSubscription(long id);

}
