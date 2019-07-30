package com.medlife.deliveries.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.medlife.deliveries.config.CustomUserTypes.JSONBUserType;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "consignment_subscriptions")
@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
        @Parameter(name = JSONBUserType.CLASS, value = "com.fasterxml.jackson.databind.JsonNode") })
public class ConsignmentSubscriptionLog extends BaseModel {

    public ConsignmentSubscriptionLog () {

    }

    @Column(name = "consignment_partner_log_id")
    private Long consignmentPartnerLogId;

    @Column(name = "consignment_subscriptions_id")
    private Long consignmentSubscriptionsId;

    @Column(name = "response")
    @Type(type = "jsonb")
    private JsonNode response;

    @Column(name = "request")
    @Type(type = "jsonb")
    private JsonNode request;

}
