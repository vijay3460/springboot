package com.medlife.deliveries.service.partner.clickPost;

import com.medlife.deliveries.clickpost.data.recommendation.RecommendationRequest;
import com.medlife.deliveries.clickpost.data.recommendation.RecommendationResponse;
import com.medlife.deliveries.clickpost.data.sla.ResultResponse;
import com.medlife.deliveries.clickpost.data.sla.SlaOptional;
import com.medlife.deliveries.clickpost.data.sla.SlaRequest;
import com.medlife.deliveries.clickpost.data.sla.SlaResponse;
import com.medlife.deliveries.clickpost.enums.DeliveryType;
import com.medlife.deliveries.clickpost.enums.OrderType;
import com.medlife.deliveries.model.ClickPostLog;
import com.medlife.deliveries.model.Consignment;
import com.medlife.deliveries.model.Fc;
import com.medlife.deliveries.model.data.Item;
import com.medlife.deliveries.model.data.PartnerDetails;
import com.medlife.deliveries.model.data.Partners;
import com.medlife.deliveries.repositories.FcRepository;
import com.medlife.deliveries.service.ConsignmentService;
import com.medlife.deliveries.service.ServiceabilityMappingService;
import com.medlife.deliveries.service.partner.DeliveryPartnerService;
import com.medlife.deliveries.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.medlife.deliveries.clickpost.data.createorder.request.*;
import com.medlife.deliveries.clickpost.data.createorder.response.CreateOrderResponse;
import com.medlife.deliveries.model.ServiceabilityMapping;
import com.medlife.deliveries.repositories.ConsignmentRepository;
import com.medlife.deliveries.repositories.DeliveryPartnerRepository;
import com.medlife.deliveries.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ClickPostService implements DeliveryPartnerService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    @Autowired
    private ConsignmentRepository consignmentRepository;

    @Autowired
    private FcRepository fcRepository;

    @Autowired
    private ClickPostRestClient restClient;

    @Autowired
    private ConsignmentService consignmentService;

    @Autowired
    private ServiceabilityMappingService serviceabilityMappingService;

    public Consignment book(Consignment consignment, ServiceabilityMapping serviceabilityMapping) {

        CreateOrderRequest createOrderRequest = convertConsignmentToCreateOrderRequest(
                consignment, serviceabilityMapping);
        CreateOrderResponse createOrderResponse = restClient.bookConsignment(createOrderRequest);
        if(createOrderResponse !=null){
            consignment.setAwb(createOrderResponse.getResultData().getWaybill());
            consignment.setLabel(createOrderResponse.getResultData().getLabel());
            consignment.setSecurityKey(createOrderResponse.getResultData().getSecurityKey());
            consignment = consignmentService.updateConsignmentById(consignment.getId(),consignment);
            return consignment;
        }
        else {
            //throw some kind of exception. like failed to book with clickPost etc.
            //save the request payload in table etc. to retry after some time.
            return consignment;
        }
    }


    public Boolean cancel(Consignment consignment) {
        return null;
    }

    public Consignment updateSla(Consignment consignment,ServiceabilityMapping serviceabilityMapping) {
        ResultResponse slaFromMapping = getSlaFromServiceabilityMapping(serviceabilityMapping);
        if(slaFromMapping!=null){
            return updateSlaInConsignment(consignment,slaFromMapping.getPredicted_sla_min(),slaFromMapping.getPredicted_sla_max());
        }
        else {
            SlaResponse slaResponse = restClient.getSla(getSlaRequest(consignment,serviceabilityMapping));
            if (slaResponse != null && StringUtils.equals(slaResponse.getMeta().getStatus(),"200")){
                ResultResponse slaFromClickPost = slaResponse.getResult().get(0);
                updateSlaInConsignment(consignment,slaFromClickPost.getPredicted_sla_min(),slaFromClickPost.getPredicted_sla_max());
                Fc fc = fcRepository.findOneById(serviceabilityMapping.getFcId());
                ClickPostLog log = DataUtils.getClickPostLogFromConsignment(consignment,serviceabilityMapping,fc);
                serviceabilityMappingService.updateSlaInMapping(serviceabilityMapping,slaFromClickPost,log);
            }
            //add some default expected SLA in case of API failure.
        }
        return consignment;
    }

    private Consignment updateSlaInConsignment(Consignment consignment,int minDays,int maxDays){
        DateTime dateTime = new DateTime();
        long maxSla = dateTime.plusDays(maxDays).toDate().getTime();
        consignment.setExpectedSla(maxSla);
        consignment = consignmentService.updateConsignmentById(consignment.getId(), consignment);
        return consignment;
    }

    private List<SlaRequest> getSlaRequest(Consignment consignment,ServiceabilityMapping serviceabilityMapping){
        Fc sourceFc = fcRepository.findOneById(consignment.getFcId());
        String pickUpPincode = sourceFc.getPincode();
        String dropPincode = consignment.getCustomerPincode();
        Long consignmentCpId = serviceabilityMapping.getDeliveryPartners().get(0).getSubpartnerId();
        String clickPostCpId = Long.toString(consignmentCpId);
        SlaOptional optional = new SlaOptional(clickPostCpId, consignment.getAwb());
        SlaRequest request = new SlaRequest(pickUpPincode, dropPincode, optional);
        List<SlaRequest> requests = new ArrayList<>();
        requests.add(request);
        return requests;
    }

    private ResultResponse getSlaFromServiceabilityMapping(ServiceabilityMapping serviceabilityMapping){
        ResultResponse slaResponse = null;
        PartnerDetails subPartner = serviceabilityMapping.getDeliveryPartners().get(0);
        if(subPartner!=null && subPartner.getPredicted_sla_max()!=null){
            slaResponse =  new ResultResponse();
            slaResponse.setMin_sla_cp_id(subPartner.getSubpartnerId().intValue());
            slaResponse.setPredicted_sla_max(subPartner.getPredicted_sla_max());
            slaResponse.setPredicted_sla_min(subPartner.getPredicted_sla_min());
        }
        return slaResponse;
    }

    private CreateOrderRequest convertConsignmentToCreateOrderRequest(Consignment consignment,ServiceabilityMapping serviceabilityMapping){

        Fc fc  = fcRepository.findOneById(consignment.getFcId());

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setDrop_info(setDropInfoDetails(consignment,fc));
        createOrderRequest.setAdditional(setAdditionalInformation(consignment,fc));
        createOrderRequest.setPickup_info(setPickInfoDetails(consignment,fc));
        createOrderRequest.setShipment_details(setShipmentDetails(consignment,serviceabilityMapping,fc));

        return createOrderRequest;
    }

    private PickupInfo setPickInfoDetails(Consignment consignment,Fc fc){
        PickupInfo pickupInfo = new PickupInfo();
        pickupInfo.setPickup_state(fc.getState());
        pickupInfo.setPickup_address(fc.getAddress());
        pickupInfo.setPickup_time(DateUtils.getCurrentTimeInIstFormattedForClickpost());
        pickupInfo.setPickup_pincode(fc.getPincode());
        pickupInfo.setPickup_city(fc.getCity());
        pickupInfo.setPickup_name(fc.getName());
        pickupInfo.setPickup_phone(fc.getPhone());
        pickupInfo.setPickup_country(fc.getCountry());
        pickupInfo.setEmail("kol@gmail.com");

        return pickupInfo;
    }

    private DropInfo setDropInfoDetails(Consignment consignment,Fc fc){
        DropInfo dropInfo = new DropInfo();
        dropInfo.setDrop_address(consignment.getCustomerAddress());
        dropInfo.setDrop_phone(consignment.getCustomerPhone());
        dropInfo.setDrop_country(consignment.getCustomerCountry());
        dropInfo.setDrop_state(consignment.getCustomerState());
        dropInfo.setDrop_pincode(consignment.getCustomerPincode());
        dropInfo.setDrop_city(consignment.getCustomerCity());
        dropInfo.setDrop_name(consignment.getCustomerName());

        return dropInfo;
    }

    private ShipmentDetails setShipmentDetails(Consignment consignment, ServiceabilityMapping serviceabilityMapping, Fc fc){
        ShipmentDetails shipmentDetails = new ShipmentDetails();
        Boolean isPrepaid =  consignment.getIsPrepaid();
        shipmentDetails.setOrder_type(getOrderType(consignment));
        shipmentDetails.setInvoice_value(consignment.getPayableAmount());
        shipmentDetails.setInvoice_number(consignment.getInvoiceNumber());
        shipmentDetails.setInvoice_date(consignment.getInvoiceDate());
        shipmentDetails.setReference_number(consignment.getExternalReferenceId());
        shipmentDetails.setLength((int)consignment.getDimensions().getLength());
        shipmentDetails.setBreadth((int)consignment.getDimensions().getBreadth());
        shipmentDetails.setWeight((int)consignment.getDimensions().getWeight());
        shipmentDetails.setHeight((int)consignment.getDimensions().getHeight());
        shipmentDetails.setCourier_partner(getCourierPartnerId(consignment,serviceabilityMapping,fc));
        shipmentDetails.setItems(getItemDetails(consignment));

        if(!isPrepaid){
            shipmentDetails.setCod_value(consignment.getPayableAmount());
        }

        return shipmentDetails;
    }

    private ReturnInfo setReturnInfoDetails(Consignment consignment,Fc fc){
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setPincode(fc.getPincode());
        returnInfo.setAddress(fc.getAddress());
        returnInfo.setState(fc.getState());
        returnInfo.setPhone(fc.getPhone());
        returnInfo.setName(fc.getName());
        returnInfo.setCity(fc.getCity());
        returnInfo.setCountry(fc.getCountry());

        return returnInfo;
    }

    private AdditionalInformation setAdditionalInformation(Consignment consignment, Fc fc){
        AdditionalInformation additionalInformation = new AdditionalInformation();
        additionalInformation.setLabel(true);
        additionalInformation.setReturn_info(setReturnInfoDetails(consignment,fc));
        additionalInformation.setDelivery_type(DeliveryType.FORWARD);
        additionalInformation.setAsync(false);

        return  additionalInformation;
    }

    private List<ItemDetail> getItemDetails(Consignment consignment){
        List<Item> items = consignment.getItems();
        List<ItemDetail>  itemDetails = new ArrayList<>();

        for(Item item:  items){
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setPrice(item.getPrice());
            itemDetail.setSku(item.getName());
            itemDetail.setDescription("");
            itemDetail.setQuantity(item.getQuantity());
            itemDetails.add(itemDetail);
        }
        return itemDetails;
    }

    private long getCourierPartnerId(Consignment consignment,ServiceabilityMapping serviceabilityMapping,Fc fc){
        long courierPartnerId = -1;
        PartnerDetails partnerDetails = serviceabilityMapping.getDeliveryPartners().get(0);
        if(partnerDetails.getId() == Partners.CLICKPOST.id()){
            courierPartnerId = getRecommendedCourierPartner(consignment,serviceabilityMapping,fc);
        }
        if(courierPartnerId == -1){
            courierPartnerId =  serviceabilityMapping.getDeliveryPartners().get(0).getSubpartnerId();
        }
        return courierPartnerId;
    }

    private long getRecommendedCourierPartner(Consignment consignment,ServiceabilityMapping serviceabilityMapping, Fc fc){
        long recommendedPartner = -1;
        PartnerDetails subPartner = serviceabilityMapping.getDeliveryPartners().get(0);
        if(subPartner!=null && subPartner.getSubpartnerId()==null){
            //subPartnerId is not present so need to be updated
            List<RecommendationRequest> requestData = getRecommendationRequestData(consignment,serviceabilityMapping,fc);
            RecommendationResponse response = restClient.getRecommendation(requestData);
            ClickPostLog log = DataUtils.getClickPostLogFromConsignment(consignment,serviceabilityMapping,fc);
            serviceabilityMapping = serviceabilityMappingService.updateRecommendedPartner(serviceabilityMapping,response,log);
            subPartner = serviceabilityMapping.getDeliveryPartners().get(0);
        }
        if(subPartner!=null && subPartner.getSubpartnerId()!=null){
            recommendedPartner =  subPartner.getSubpartnerId();
        }
        return recommendedPartner;
    }

    private List<RecommendationRequest> getRecommendationRequestData(Consignment consignment, ServiceabilityMapping serviceabilityMapping,Fc fc){
        List<RecommendationRequest> recommendationRequests = new ArrayList<>();
        RecommendationRequest request = new RecommendationRequest();
        request.setBreadth((int)consignment.getDimensions().getBreadth());
        request.setLength((int)consignment.getDimensions().getLength());
        request.setHeight((int)consignment.getDimensions().getHeight());
        request.setWeight((int)consignment.getDimensions().getWeight());
        request.setDelivery_type(getDeliveryType(consignment));
        request.setInvoice_value(consignment.getValueDetails().getTotalAmount());
        request.setOrder_type(getOrderType(consignment));
        request.setDrop_pincode(consignment.getCustomerPincode());
        request.setPickup_pincode(fc.getPincode());
        request.setReference_number(consignment.getInvoiceNumber());
        request.setItem("Medicine Box");
        recommendationRequests.add(request);

        return recommendationRequests;
    }

    private DeliveryType getDeliveryType(Consignment consignment){
        //Configure it on the basis of consignment
        return DeliveryType.FORWARD;
    }

    private String getOrderType(Consignment consignment){
        Boolean isPrepaid =  consignment.getIsPrepaid();

        if(isPrepaid) {
            return OrderType.PREPAID.name();
        }
        return OrderType.COD.name();
    }

}
