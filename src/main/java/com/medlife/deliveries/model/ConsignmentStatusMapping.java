package com.medlife.deliveries.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "consignment_status_mappings")
@Data
public class ConsignmentStatusMapping extends BaseModel{

    public ConsignmentStatusMapping () {

    }

    @Column(name = "processed_status")
    private String processedStatus;

    @Column(name = "unprocessed_status")
    private String unprocessedStatus;

    @Column(name = "unprocessed_status_id")
    private Long unprocessedStatusId;

    @Column(name = "delivery_partner_id")
    private Long deliveryPartnerId;

}
