package com.medlife.deliveries.service;

import com.medlife.deliveries.model.ConsignmentConsolidation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Harshit Singh Chauhan
 */
@Service
public interface ConsignmentConsolidationService {

    List<ConsignmentConsolidation> getAllConsignmentConsolidations();

    List<ConsignmentConsolidation> insertNewConsignmentConsolidations(List<ConsignmentConsolidation> consignmentConsolidations);

    List<ConsignmentConsolidation> updateConsignmentConsolidations(List<ConsignmentConsolidation> consignmentConsolidations);

    void deleteConsignmentConsolidation(long id);

}
