package com.medlife.deliveries.model;

import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "serviceability_mapping_logs")
public class ServiceabilityMappingLog extends BaseModel {

	public ServiceabilityMappingLog () {

	}
	
	@Column(name = "serviceability_mapping_id")
	private Long serviceabilityMappingId;
	
	@Column(name = "origin_area")
	private Long originArea;
	
	@Column(name = "destination_area")
	private String destinationArea;
	
	@Column(name = "mode")
	private String mode;
	
	@Column(name = "delivery_partners")
	@ElementCollection(targetClass=Long.class)
	private List<Long> deliveryPartners;
	
	@Column(name = "is_refrigerated_allowed")
	private Boolean isRefrigeratedAllowed;
	
	@Column(name = "priority")
	private Long priority;

}
