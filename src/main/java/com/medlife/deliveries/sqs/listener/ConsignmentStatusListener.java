package com.medlife.deliveries.sqs.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ConsignmentPartnerLog;
import com.medlife.deliveries.model.ConsignmentStatusLog;
import com.medlife.deliveries.model.ConsignmentStatusMapping;
import com.medlife.deliveries.model.enums.DeliveryType;
import com.medlife.deliveries.repositories.ConsignmentPartnerLogRepository;
import com.medlife.deliveries.repositories.ConsignmentRepository;
import com.medlife.deliveries.repositories.ConsignmentStatusLogRepository;
import com.medlife.deliveries.repositories.ConsignmentStatusMappingRepository;
import com.medlife.deliveries.service.ConsignmentStatusNotificationRestClient;
import com.medlife.deliveries.sqs.data.ConsignmentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ConsignmentStatusListener {

    @Autowired
    ConsignmentStatusLogRepository consignmentStatusLogRepository;

    @Autowired
    ConsignmentPartnerLogRepository consignmentPartnerLogRepository;

    @Autowired
    ConsignmentRepository consignmentRepository;

    @Autowired
    ConsignmentStatusMappingRepository consignmentStatusMappingRepository;

    @Autowired
    ConsignmentStatusNotificationRestClient consignmentStatusNotificationRestClient;

    @JmsListener(destination = "")
    public void receive(@Payload String message) throws IOException {
        log.info("Got consignment status {}", message);
        ConsignmentStatus consignmentStatus = new ObjectMapper().readValue(
                    message, ConsignmentStatus.class );
        try{
            ConsignmentStatusMapping consignmentStatusMapping = consignmentStatusMappingRepository.findOneById(
                    consignmentStatus.getConsignmentStatusMappingId()
            );
            Consignment consignment = consignmentRepository.findOneByAwbAndDeliveryType(
                    consignmentStatus.getAwb(), DeliveryType.OUTBOUND.value());

            if(consignment == null){
                throw new Exception("Consignment not found for awb: " + consignmentStatus.getAwb());
            }

            ConsignmentStatusLog consignmentStatusLog = createConsignmentStatusLog(
                    consignmentStatus, consignment, consignmentStatusMapping, false);
            consignment.setStatus(consignmentStatusMapping.getProcessedStatus());
            consignmentRepository.save(consignment);
            consignmentStatusNotificationRestClient.notifyConsignmentStatus(consignment, consignmentStatusLog);
        } catch (Exception e) {
            log.error("Error while processing message: {}", message);

        }

    }

    private ConsignmentStatusLog createConsignmentStatusLog(
            ConsignmentStatus consignmentStatus, Consignment consignment,
            ConsignmentStatusMapping consignmentStatusMapping,Boolean notified
    ){

        ConsignmentPartnerLog consignmentPartnerLog = new ConsignmentPartnerLog();
        consignmentPartnerLog.setReceivedData(consignmentStatus.getDeliveryPartnerPayload());
        consignmentPartnerLog.setReceivedType("POLL");
        consignmentPartnerLog = consignmentPartnerLogRepository.saveAndFlush(consignmentPartnerLog);

        ConsignmentStatusLog consignmentStatusLog = new ConsignmentStatusLog();
        List<ConsignmentStatusLog> existingConsignmentStatusLogs = consignmentStatusLogRepository.
                findByConsignmentIdAndStatus(consignment.getId(), consignmentStatusMapping.getProcessedStatus());
        if(!existingConsignmentStatusLogs.isEmpty()){
            log.warn("Duplicate status received for consignment_id {}", consignment.getId());
        }

        consignmentStatusLog.setStatus(consignmentStatusMapping.getProcessedStatus());
        consignmentStatusLog.setConsignmentPartnerLogId(consignmentPartnerLog.getId());
        consignmentStatusLog.setNotified(notified);
        ConsignmentStatusLog persistedLog = consignmentStatusLogRepository.save(consignmentStatusLog);

        return persistedLog;
    }

}

