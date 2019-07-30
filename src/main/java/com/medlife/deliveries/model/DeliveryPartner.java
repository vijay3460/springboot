package com.medlife.deliveries.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.databind.JsonNode;
import com.medlife.deliveries.config.CustomUserTypes.JSONBUserType;

@Data
@Entity
@Table(name = "delivery_partners")
@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
		@Parameter(name = JSONBUserType.CLASS, value = "com.fasterxml.jackson.databind.JsonNode") })
public class DeliveryPartner extends BaseModel {

	public DeliveryPartner() {

	}

	@NotBlank
	@Column(name = "name")
	private String name;

	@Column(name = "details")
	@Type(type = "jsonb")
	private JsonNode details;

	@NotNull
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@NotBlank
	@Column(name = "partner_type", nullable = false)
	private String partnerType;

	@ApiModelProperty(hidden = true)
	@Column(name = "deleted_at")
	private Long deletedAt;

	@NotNull
	@Column(name = "is_recommendation_enabled")
	private Boolean isRecommendationEnabled;

}
