package com.medlife.deliveries.model;

import com.medlife.deliveries.config.CustomUserTypes.JSONBUserType;
import com.medlife.deliveries.model.data.PartnerDetails;
import com.medlife.deliveries.utils.MapperUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Entity
@Table(name = "serviceability_mappings")
@TypeDef(name = "partner-type", typeClass = JSONBUserType.class, parameters = {
		@org.hibernate.annotations.Parameter(name = JSONBUserType.CLASS, value = "java.util.ArrayList") })
public class ServiceabilityMapping extends BaseModel {

	public ServiceabilityMapping () {

	}

	@NotNull
	@Column(name = "fc_id")
	private Long fcId;

	@NotNull
	@Column(name = "area_mapping_id")
	private Long areaMappingId;

	@NotNull
	@Column(name = "mode")
	private String mode;


	@NotEmpty
	@Column(name = "delivery_partners")
	@Type(type = "partner-type")
	private List<@Valid PartnerDetails> deliveryPartners;

	@NotNull
	@Column(name = "is_refrigerated_allowed")
	private Boolean isRefrigeratedAllowed;

	@NotNull
	@Column(name = "priority")
	private Long priority;

	@ApiModelProperty(hidden = true)
	@Column(name = "deleted_at")
	private Long deletedAt;

	// to map ArrayList Objects into Correct Class Type after the data is loaded into the object
	@PostLoad
	public void postLoad(){
		this.deliveryPartners = MapperUtils.getClassTypeListFromHashMapType(this.deliveryPartners,PartnerDetails.class);
	}

	@PostUpdate
	public void postUpdate(){
		this.deliveryPartners = this.deliveryPartners.stream().map( x -> MapperUtils.getDeepCopy(x,PartnerDetails.class)).collect(Collectors.toList());
	}

}
