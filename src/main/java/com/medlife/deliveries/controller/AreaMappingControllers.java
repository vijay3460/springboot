package com.medlife.deliveries.controller;

import com.medlife.deliveries.model.AreaMapping;
import com.medlife.deliveries.service.AreaMappingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */

@RestController
@RequestMapping(value = "/areas",consumes = "application/json",produces = "application/json")
public class AreaMappingControllers {

    @Autowired
    private AreaMappingService areaMappingService;

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    ResponseEntity<AreaMapping> getAreaMappingById(@PathVariable Long id){
        return new ResponseEntity<>(areaMappingService.getAreaMappingById(id),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<AreaMapping>> getAreaMappings(@RequestParam(required = false) String area){
        if(StringUtils.isNotBlank(area))
            return new ResponseEntity<>(areaMappingService.getAreaMappingsByAreaName(area),HttpStatus.OK);
        else
            return new ResponseEntity<>(areaMappingService.getAllAreaMappings(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<List<AreaMapping>> insertNewAreaMapping(@RequestBody List<AreaMapping> areaMappings){
        return new ResponseEntity<>(areaMappingService.insertNewAreaMappings(areaMappings),HttpStatus.CREATED);
    }


    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    ResponseEntity<AreaMapping> updateAreaMappingById(@PathVariable Long id,@RequestBody AreaMapping areaMapping){
        return new ResponseEntity<>(areaMappingService.updateAreaMappingById(id,areaMapping),HttpStatus.OK);
    }

    @RequestMapping(value="/bulk",method = RequestMethod.PUT)
    ResponseEntity<List<AreaMapping>> updateAreaMappings(@RequestBody List<AreaMapping> areaMappings){
        return new ResponseEntity<>(areaMappingService.updateAreaMappings(areaMappings),HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    ResponseEntity deleteAreaMapping(@PathVariable long id){
        areaMappingService.deleteAreaMapping(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
