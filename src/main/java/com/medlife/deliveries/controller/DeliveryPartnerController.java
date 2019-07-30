package com.medlife.deliveries.controller;

import com.medlife.deliveries.model.DeliveryPartner;
import com.medlife.deliveries.service.DeliveryPartnerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@RestController
@RequestMapping(value = "/partners",consumes = "application/json",produces = "application/json")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    ResponseEntity<DeliveryPartner> getDeliveryPartnerById(@PathVariable Long id){
        return new ResponseEntity<>(deliveryPartnerService.getDeliveryPartnerById(id),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<DeliveryPartner>> getDeliveryPartners(@RequestParam(required = false) String name){
        if(StringUtils.isNotBlank(name))
            return new ResponseEntity<>(Arrays.asList(deliveryPartnerService.getDeliveryPartnerByName(name)),HttpStatus.OK);
        else
            return new ResponseEntity<>(deliveryPartnerService.getAllDeliveryPartners(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<List<DeliveryPartner>> insertDeliveryPartners(@RequestBody List<DeliveryPartner> deliveryPartners){
        return new ResponseEntity<>(deliveryPartnerService.insertDeliveryPartners(deliveryPartners),HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    ResponseEntity<DeliveryPartner> updateDeliveryPartnerById(@PathVariable Long id,@RequestBody DeliveryPartner deliveryPartners){
        return new ResponseEntity<>(deliveryPartnerService.updateDeliveryPartnerById(id,deliveryPartners),HttpStatus.OK);
    }

    @RequestMapping(value="/bulk",method = RequestMethod.PUT)
    ResponseEntity<List<DeliveryPartner>> updateDeliveryPartners(@RequestBody List<DeliveryPartner> deliveryPartners){
        return new ResponseEntity<>(deliveryPartnerService.updateDeliveryPartners(deliveryPartners),HttpStatus.OK);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    ResponseEntity deleteDeliveryPartner(@PathVariable(name = "id") Long id){
        deliveryPartnerService.deleteDeliveryPartner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
