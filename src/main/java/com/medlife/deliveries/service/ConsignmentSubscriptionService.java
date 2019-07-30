package com.medlife.deliveries.service;

import com.medlife.deliveries.model.ConsignmentSubscription;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface ConsignmentSubscriptionService {

    List<ConsignmentSubscription> getAllSubscriptions(Long consignmentId);

    ConsignmentSubscription getSubscriptionById(Long consignmentId,Long subscriptionId);

    List<ConsignmentSubscription> insertNewSubscriptions(Long consignmentId,List<ConsignmentSubscription> consignmentSubscriptions);

    ConsignmentSubscription updateSubscriptionById(Long consignmentId,Long subscriptionId,ConsignmentSubscription subscription);

    List<ConsignmentSubscription> updateSubscriptions(Long consignmentId,List<ConsignmentSubscription> consignmentSubscriptions);

    void deleteSubscription(Long consignmentId,long subscriptionId);


}
