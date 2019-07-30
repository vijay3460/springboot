package com.medlife.deliveries.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.medlife.deliveries.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ApiModelProperty(hidden = true)
	@Column(name = "created_at", nullable = false, updatable = false)
	private Long createdAt = 0l;

	@ApiModelProperty(hidden = true)
	@Column(name = "updated_at", nullable = false)
	private Long updatedAt = 0l;

	@PrePersist
	public void onPrePersist() {
		this.createdAt = DateUtils.getCurrentTimestampInSeconds();
		this.updatedAt = DateUtils.getCurrentTimestampInSeconds();
	}

	@PreUpdate
	public void onPreUpdate() {
		this.updatedAt = DateUtils.getCurrentTimestampInSeconds();
	}

}
