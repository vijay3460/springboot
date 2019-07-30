package com.medlife.deliveries.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "consignment_status_logs")
@Data
public class ConsignmentStatusLog extends BaseModel{
	
	public ConsignmentStatusLog () {

	}
	
	@Column(name = "consignment_id")
	private Long consignmentId;

	@Column(name = "status")
	private String status;

	@Column(name = "consignment_partner_log_id")
	private Long consignmentPartnerLogId ;

	@Column(name = "notified")
	private Boolean notified ;

}
