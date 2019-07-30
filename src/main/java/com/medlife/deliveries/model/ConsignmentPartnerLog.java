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
@Table(name = "consignment_partner_logs")
@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
        @Parameter(name = JSONBUserType.CLASS, value = "com.fasterxml.jackson.databind.JsonNode") })
public class ConsignmentPartnerLog extends BaseModel{

    public ConsignmentPartnerLog () {

    }

    @Column(name = "received_data")
    @Type(type = "jsonb")
    private JsonNode receivedData;

    @Column(name = "received_type")
    private String receivedType;

}
