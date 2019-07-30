package com.medlife.deliveries.service;

import com.medlife.deliveries.model.ConsignmentSubscription;
import com.medlife.deliveries.repositories.ConsignmentSubscriptionRepository;
import com.medlife.deliveries.service.ApiRequestValidations.ValidationService;
import com.medlife.deliveries.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public class ConsignmentSubscriptionServiceImpl implements ConsignmentSubscriptionService {

    @Autowired
    private ConsignmentSubscriptionRepository consignmentSubscriptionRepository;

    @Autowired
    private ValidationService<ConsignmentSubscription,ConsignmentSubscription> subscriptionValidation;

    @Override
    public List<ConsignmentSubscription> getAllSubscriptions(Long consignmentId) {
        return consignmentSubscriptionRepository.findAllByConsignmentId(consignmentId);
    }

    @Override
    public ConsignmentSubscription getSubscriptionById(Long consignmentId,Long subscriptionId) {
        return consignmentSubscriptionRepository.findOneById(subscriptionId);
    }

    @Override
    public List<ConsignmentSubscription> insertNewSubscriptions(Long consignmentId, List<ConsignmentSubscription> consignmentSubscriptions) {
        List<ConsignmentSubscription> insertedConsignmentSubscriptions = new ArrayList<>();
        //validate
        //format
        for(ConsignmentSubscription consignmentSubscription : consignmentSubscriptions){
            consignmentSubscription.setConsignmentId(consignmentId);
            subscriptionValidation.validateApiRequest(consignmentSubscription);
            consignmentSubscription = subscriptionValidation.getEntityFromRequest(consignmentSubscription);
            ConsignmentSubscription newConsignmentSubscription = consignmentSubscriptionRepository.save(consignmentSubscription);
            insertedConsignmentSubscriptions.add(newConsignmentSubscription);
        }
        return insertedConsignmentSubscriptions;
    }

    @Override
    public ConsignmentSubscription updateSubscriptionById(Long consignmentId,Long subscriptionId, ConsignmentSubscription subscription) {
        subscription.setId(subscriptionId);
        return updateSubscriptions(consignmentId,Arrays.asList(subscription)).get(0);
    }

    @Override
    public List<ConsignmentSubscription> updateSubscriptions(Long consignmentId,List<ConsignmentSubscription> consignmentSubscriptions) {
        List<ConsignmentSubscription> updatedConsignmentSubscriptions = new ArrayList<>();
        //validate
        //format
        for(ConsignmentSubscription consignmentSubscription : consignmentSubscriptions){
            consignmentSubscription.setConsignmentId(consignmentId);
            ConsignmentSubscription updatedConsignmentSubscription = consignmentSubscriptionRepository.save(consignmentSubscription);
            updatedConsignmentSubscriptions.add(updatedConsignmentSubscription);
        }
        return updatedConsignmentSubscriptions;
    }

    @Override
    public void deleteSubscription(Long consignmentId,long id) {
        ConsignmentSubscription subscription = consignmentSubscriptionRepository.findOneById(id);
        subscription.setDeletedAt(DateUtils.getCurrentTimestampInSeconds());
        consignmentSubscriptionRepository.save(subscription);
    }
}
