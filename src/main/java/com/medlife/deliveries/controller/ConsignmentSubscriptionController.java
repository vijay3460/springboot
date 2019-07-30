package com.medlife.deliveries.controller;

import com.medlife.deliveries.model.ConsignmentSubscription;
import com.medlife.deliveries.service.ConsignmentSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@RestController
@RequestMapping(value = "consignment/{consignmentId}/subscriptions" ,consumes = "application/json",produces = "application/json")
public class ConsignmentSubscriptionController {

    @Autowired
    private ConsignmentSubscriptionService consignmentSubscriptionService;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<ConsignmentSubscription>> getAllSubscriptions(@PathVariable Long consignmentId){
        return new ResponseEntity<>(consignmentSubscriptionService.getAllSubscriptions(consignmentId),HttpStatus.OK);
    }

    @RequestMapping(value="/{subscriptionId}",method = RequestMethod.GET)
    ResponseEntity<ConsignmentSubscription> getSubscriptionById(@PathVariable Long consignmentId,@PathVariable Long subscriptionId){
        return new ResponseEntity<>(consignmentSubscriptionService.getSubscriptionById(consignmentId,subscriptionId),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<List<ConsignmentSubscription>> addNewSubscriptions(@PathVariable Long consignmentId,@RequestBody List<ConsignmentSubscription> subscriptions){
        return new ResponseEntity<>(consignmentSubscriptionService.insertNewSubscriptions(consignmentId,subscriptions),HttpStatus.CREATED);
    }

    @RequestMapping(value="/{subscriptionId}",method = RequestMethod.PUT)
    ResponseEntity<ConsignmentSubscription> updateSubscriptionById(@PathVariable Long consignmentId,
                                                                   @PathVariable Long subscriptionId,@RequestBody ConsignmentSubscription subscription){
        return new ResponseEntity<>(consignmentSubscriptionService.updateSubscriptionById(consignmentId,subscriptionId,subscription),HttpStatus.OK);
    }

    @RequestMapping(value="/bulk",method = RequestMethod.PUT)
    ResponseEntity<List<ConsignmentSubscription>> updateSubscriptions(@PathVariable Long consignmentId,@RequestBody List<ConsignmentSubscription> subscriptions){
        return new ResponseEntity<>(consignmentSubscriptionService.updateSubscriptions(consignmentId,subscriptions),HttpStatus.OK);
    }

    @RequestMapping(value="/{subscriptionId}",method = RequestMethod.DELETE)
    ResponseEntity deleteSubscription(@PathVariable Long consignmentId,@PathVariable Long subscriptionId){
        consignmentSubscriptionService.deleteSubscription(consignmentId,subscriptionId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
