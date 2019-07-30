package com.medlife.deliveries.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "area_mappings")
public class AreaMapping extends BaseModel {

	public AreaMapping () {

	}

	@NotBlank
	@Column(name = "area")
	private String area;

	@Pattern(regexp = "//d{6}",message = "Pincode Must be of 6 digits only")
	@Column(name = "pincode")
	private Long pincode;

	@ApiModelProperty(hidden = true)
	@Column(name = "deleted_at")
	private Long deletedAt;


}
