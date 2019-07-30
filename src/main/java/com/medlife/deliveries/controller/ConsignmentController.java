package com.medlife.deliveries.controller;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.service.partner.clickPost.ClickPostService;
import com.medlife.deliveries.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@RestController
@RequestMapping(value = "/consignments" ,consumes = "application/json",produces = "application/json")
public class ConsignmentController {

    @Autowired
    private ConsignmentService consignmentService;

    @Autowired
    private ClickPostService clickPostService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    ResponseEntity<Consignment> getConsignmentById(@PathVariable Long id){
        return new ResponseEntity<>(consignmentService.getConsignmentById(id),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Consignment>> getAllConsignments(){
        return new ResponseEntity<>(consignmentService.getAllConsignments(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<List<Consignment>> insertNewConsignments(@RequestBody List<Consignment> consignments){
        return new ResponseEntity<>(consignmentService.insertNewConsignments(consignments),HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    ResponseEntity<Consignment> updateConsignmentById(@PathVariable Long id,@RequestBody Consignment consignment){
        return new ResponseEntity<>(consignmentService.updateConsignmentById(id,consignment),HttpStatus.OK);
    }

    @RequestMapping(value="/bulk",method = RequestMethod.PUT)
    ResponseEntity<List<Consignment>> updateConsignments(@RequestBody List<Consignment> consignments){
        return new ResponseEntity<>(consignmentService.updateConsignments(consignments),HttpStatus.OK);
    }


}
