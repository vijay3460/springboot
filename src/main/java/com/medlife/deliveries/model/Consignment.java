package com.medlife.deliveries.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.medlife.deliveries.config.CustomUserTypes.JSONBUserType;
import com.medlife.deliveries.model.data.Dimensions;
import com.medlife.deliveries.model.data.Item;
import com.medlife.deliveries.model.data.ValueDetails;

import com.medlife.deliveries.utils.MapperUtils;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "consignments")
@TypeDef(name = "dimension-type", typeClass = JSONBUserType.class, parameters = {
		@Parameter(name = JSONBUserType.CLASS, value = "com.medlife.deliveries.model.data.Dimensions") })
@TypeDef(name = "value-details-type", typeClass = JSONBUserType.class, parameters = {
		@Parameter(name = JSONBUserType.CLASS, value = "com.medlife.deliveries.model.data.ValueDetails") })
@TypeDef(name = "items-type", typeClass = JSONBUserType.class, parameters = {
		@Parameter(name = JSONBUserType.CLASS, value = "java.util.ArrayList") })
@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
		@Parameter(name = JSONBUserType.CLASS, value = "com.fasterxml.jackson.databind.JsonNode") })
public class Consignment extends BaseModel {

	public Consignment () {

	}

	@Column(name = "status")
	private String status;

	@NotBlank
	@Column(name = "external_reference_id")
	private String externalReferenceId;
	
	@Column(name = "partner_id", nullable = false)
	private Long partnerId;

	@Column(name = "awb")
	private String awb;
	
	@Column(name = "expected_sla")
	private Long expectedSla;
	
	@Column(name = "actual_sla")
	private Long actualSla;

	@NotNull
	@Column(name = "fc_id")
	private Long fcId;

	@NotNull
	@Column(name = "payable_amount")
	private Double payableAmount;
	
	@Column(name = "is_lost_by_partner")
	private Boolean isLostByPartner;

	@Column(name = "details")
	@Type(type = "jsonb")
	private JsonNode details;
	
	@Column(name = "booked_at")
	private Long bookedAt;
	
	@Column(name = "picked_at")
	private Long pickedAt;
	
	@Column(name = "delivered_at")
	private Long deliveredAt;
	
	@Column(name = "first_delivered_at")
	private Long firstDeliveredAt;
	
	@Column(name = "rto_initiated_at")
	private Long rtoInitiatedAt;
	
	@Column(name = "rto_delivered_at")
	private Long rtoDeliveredAt;

	@NotBlank
	@Column(name = "customer_name")
	private String customerName;

	@NotBlank
	@Column(name = "customer_address")
	private String customerAddress;

	@NotBlank
	@Column(name = "customer_city")
	private String customerCity;

	@NotBlank
	@Column(name = "customer_state")
	private String customerState;

	@NotBlank
    @Column(name = "customer_pincode")
    private String customerPincode;

    @Column(name = "customer_country")
    private String customerCountry;

	@NotBlank
    @Column(name = "customer_phone")
    private String customerPhone;

	@NotNull
	@Valid
	@Column(name = "dimensions")
	@Type(type = "dimension-type")
	private Dimensions dimensions;

	@NotNull
	@Valid
	@Column(name="value_details")
	@Type(type="value-details-type")
	private ValueDetails valueDetails;

	@Column(name = "is_prepaid")
	private Boolean isPrepaid;

	@Column(name = "delivery_type")
	private String deliveryType;

	@NotNull
	@Column(name = "is_refrigerated")
	private Boolean isRefrigerated;

	@NotBlank
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@NotBlank
	@Column(name = "invoice_date")
	private String invoiceDate;

	@Column(name = "security_key")
	private String securityKey;

	@Column(name = "label")
	private String label;

	@NotEmpty
	@Column(name="items")
	@Type(type="items-type")
	private List<@Valid Item> items;

	@PostLoad
	public void postLoad(){
		this.items = MapperUtils.getClassTypeListFromHashMapType(this.items, Item.class);
	}

	@PostUpdate
	public void postUpdate(){
		this.items = this.items.stream().map( x -> MapperUtils.getDeepCopy(x,Item.class)).collect(Collectors.toList());
	}

}
