package com.medlife.deliveries.service.partner.clickPost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medlife.deliveries.clickpost.data.createorder.request.CreateOrderRequest;
import com.medlife.deliveries.clickpost.data.createorder.response.CreateOrderResponse;
import com.medlife.deliveries.clickpost.data.recommendation.RecommendationRequest;
import com.medlife.deliveries.clickpost.data.recommendation.RecommendationResponse;
import com.medlife.deliveries.clickpost.data.sla.SlaRequest;
import com.medlife.deliveries.clickpost.data.sla.SlaResponse;
import com.medlife.deliveries.utils.MapperUtils;
import com.medlife.deliveries.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


/**
 * @author : Harshit Singh Chauhan
 */
@Slf4j
@Service
public class ClickPostRestClient {


    @Value("${clickpost.api_key}")
    private String clickPostApiKey;

    @Value("${clickpost.user_name}")
    private String clickPostUserName;

    @Value("${clickpost.base_url}")
    private String clickPostBaseUrl;


    public SlaResponse getSla(List<SlaRequest> request) {
        try {
            String slaUrl = clickPostBaseUrl.concat("v1/predicted_sla_api/");
            RestTemplate restTemplate = RestTemplateUtil.getRestTemplate(5000);
            HttpHeaders headers = new HttpHeaders(RestTemplateUtil.getHttpHeaders());
            HttpEntity<List<SlaRequest>> entity = new HttpEntity(request,headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(slaUrl)
                    .queryParam("username",clickPostUserName)
                    .queryParam("key",clickPostApiKey);
            ResponseEntity<SlaResponse> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, SlaResponse.class);
            log.info("getClickPostSla : For payload : {}, the response is : {}",MapperUtils.getJSONString(request),MapperUtils.getJSONString(response));
            return response.getBody();
        } catch (Exception e) {
            log.error("getClickPostSla : Error While Fetching Sla for payload : {} and exception is : ",MapperUtils.getJSONString(request),e);
            return null;
        }
    }

    public CreateOrderResponse bookConsignment(CreateOrderRequest createOrderRequest) {
        try{
            log.info("bookConsignmentWithClickPost : trying to book consignment for invoiceNum : {}  and payload : {}",
                    createOrderRequest.getShipment_details().getInvoice_number(),MapperUtils.getJSONString(createOrderRequest));
            String createOrderUrl = clickPostBaseUrl.concat("v3/create-order/");
            RestTemplate restTemplate = RestTemplateUtil.getRestTemplate(15000);
            HttpHeaders httpHeaders = new HttpHeaders(RestTemplateUtil.getHttpHeaders());
            HttpEntity<CreateOrderRequest> entity = new HttpEntity<>(createOrderRequest, httpHeaders);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createOrderUrl)
                    .queryParam("username",clickPostUserName)
                    .queryParam("key",clickPostApiKey);
            ResponseEntity<CreateOrderResponse> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST,entity,CreateOrderResponse.class);
            if (response.getBody().getResultData().getWaybill() == null){
                throw new Exception("Could not book consignment successfully");
            }
            log.info("bookConsignmentWithClickPost - booked for consignment invoiceNum : {} and response is : {}",
                    createOrderRequest.getShipment_details().getInvoice_number(),MapperUtils.getJSONString(response));
            return response.getBody();
        }
        catch (Exception e){
            log.error("bookConsignmentWithClickPost : failed for consignmentId : {} and exception is : ",createOrderRequest.getShipment_details().getReference_number(),e);
            return null;
        }
    }

    public RecommendationResponse getRecommendation(List<RecommendationRequest> requests){
        try{
            log.info("getRecommendationOfClickPost : trying to get Recommendataions for payload : {}",MapperUtils.getJSONString(requests));
            String recommendationUrl = clickPostBaseUrl.concat("v1/recommendation_api/");
            RestTemplate restTemplate = RestTemplateUtil.getRestTemplate(5000);
            HttpHeaders httpHeaders = new HttpHeaders(RestTemplateUtil.getHttpHeaders());
            HttpEntity<List<RecommendationRequest>> entity = new HttpEntity<>(requests,httpHeaders);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(recommendationUrl)
                    .queryParam("key",clickPostApiKey);
            ResponseEntity<RecommendationResponse> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST,entity,RecommendationResponse.class);
            log.info("getRecommendationOfClickPost : response is : {}",MapperUtils.getJSONString(response));
            return response.getBody();
        }
        catch (Exception e){
            log.error("getRecommendationOfClickPost : the exception is : ",e);
            return null;
        }
    }
}
