package com.medlife.deliveries.model;

import lombok.Data;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "consignment_consolidations")
public class ConsignmentConsolidation extends BaseModel {

	public ConsignmentConsolidation () {

	}

	@Column(name = "status")
	private String status;
	
	@Column(name = "deleted_at")
	private Long deletedAt;
	
	@Column(name = "consignment_ids")
	@ElementCollection(targetClass=Long.class)
	private List <Long> consignmentIds;

}
