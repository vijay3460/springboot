package com.medlife.deliveries.service;

import com.medlife.deliveries.model.Consignment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface ConsignmentService {

    List<Consignment> getAllConsignments();

    Consignment getConsignmentById(Long id);

    List<Consignment> insertNewConsignments(List<Consignment> consignments);

    Consignment updateConsignmentById(Long id,Consignment consignment);

    List<Consignment> updateConsignments(List<Consignment> consignments);

}
