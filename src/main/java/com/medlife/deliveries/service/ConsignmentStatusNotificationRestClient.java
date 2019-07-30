package com.medlife.deliveries.service;

import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.ConsignmentStatusLog;
import com.medlife.deliveries.model.ConsignmentSubscription;
import com.medlife.deliveries.repositories.ConsignmentStatusLogRepository;
import com.medlife.deliveries.repositories.ConsignmentSubscriptionRepository;
import com.medlife.deliveries.sqs.data.ConsignmentStatusNotificationRequest;
import com.medlife.deliveries.utils.MapperUtils;
import com.medlife.deliveries.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class ConsignmentStatusNotificationRestClient {

    @Autowired
    ConsignmentSubscriptionRepository consignmentSubscriptionRepository;

    @Autowired
    ConsignmentStatusLogRepository consignmentStatusLogRepository;

    public void notifyConsignmentStatus(Consignment consignment, ConsignmentStatusLog consignmentStatusLog) {
        try {

            ConsignmentSubscription consignmentSubscription = consignmentSubscriptionRepository.findOneByConsignmentId(
                    consignment.getId());
            if(consignmentSubscription == null){
                return;
            }
            if(!consignmentSubscription.getSubscribedStatuses().contains(consignmentStatusLog.getStatus())){
                return;
            }
            ConsignmentStatusNotificationRequest consignmentStatusNotificationRequest = new ConsignmentStatusNotificationRequest();
            consignmentStatusNotificationRequest.setConsignmentId(consignment.getId());
            consignmentStatusNotificationRequest.setExternalReferenceID(consignment.getExternalReferenceId());
            consignmentStatusNotificationRequest.setStatus(consignmentStatusLog.getStatus());

            RestTemplate restTemplate = RestTemplateUtil.getRestTemplate(5000);
            HttpHeaders headers = consignmentSubscription.getHeaders();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(consignmentSubscription.getEndpoint());
            HttpEntity<ConsignmentStatusNotificationRequest> entity = new HttpEntity<>(consignmentStatusNotificationRequest, headers);
            ResponseEntity<?> response = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.valueOf(consignmentSubscription.getHttpMethod()),
                    entity, Object.class);
            log.info("notified consignment status for consignment_id: {}, response: {}",
                   consignment.getId(), MapperUtils.getJSONString(response));
            consignmentStatusLog.setNotified(true);
            consignmentStatusLogRepository.save(consignmentStatusLog);

        } catch (Exception e) {
            log.error("Got exception while notifying consignment status: {}",e);
        }
    }
}
