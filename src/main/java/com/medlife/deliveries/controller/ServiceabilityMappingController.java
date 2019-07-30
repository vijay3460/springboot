package com.medlife.deliveries.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medlife.deliveries.clickpost.data.sla.ResultResponse;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.model.data.PartnerDetails;
import com.medlife.deliveries.model.data.ServiceabilitySlaRequest;
import com.medlife.deliveries.model.data.ServiceabilitySlaResponse;
import com.medlife.deliveries.model.data.ServiceablePartners;
import com.medlife.deliveries.repositories.ServiceabilityMappingRepository;
import com.medlife.deliveries.service.ServiceabilityMappingService;
import com.medlife.deliveries.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
@RestController
@RequestMapping(value="/serviceability",consumes = "application/json",produces = "application/json")
public class ServiceabilityMappingController {

    @Autowired
    private ServiceabilityMappingService serviceabilityMappingService;

    @Autowired
    private ServiceabilityMappingRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<ServiceabilityMapping>> getAllMappings(){
        return new ResponseEntity<>(serviceabilityMappingService.getAllMappings(),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    ResponseEntity<ServiceabilityMapping> getMappingById(@PathVariable Long id){
        return new ResponseEntity<>(serviceabilityMappingService.getMappingById(id),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<List<ServiceabilityMapping>> insertNewMappings(@RequestBody List<ServiceabilityMapping> newMappings){
        return new ResponseEntity<>(serviceabilityMappingService.insertNewMappings(newMappings),HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    ResponseEntity<ServiceabilityMapping> updateMappingById(@PathVariable Long id,@RequestBody ServiceabilityMapping updatedMapping){
        return new ResponseEntity<>(serviceabilityMappingService.updateMappingById(id,updatedMapping),HttpStatus.OK);
    }

    @RequestMapping(value="/bulk",method = RequestMethod.PUT)
    ResponseEntity<List<ServiceabilityMapping>> updateMappings(@RequestBody List<ServiceabilityMapping> updatedMappings){
        return new ResponseEntity<>(serviceabilityMappingService.updateMappings(updatedMappings),HttpStatus.OK);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    ResponseEntity deleteMapping(@PathVariable long id){
        serviceabilityMappingService.deleteMapping(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/sla",method = RequestMethod.POST)
    ResponseEntity<ServiceabilitySlaResponse> getServiceabilitySla(@RequestBody ServiceabilitySlaRequest slaRequest){
        return new ResponseEntity<>(serviceabilityMappingService.getServiceabilitySlaResponse(slaRequest),HttpStatus.OK);
    }

}