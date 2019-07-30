package com.medlife.deliveries.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

import com.medlife.deliveries.utils.JsonTransformer;
import com.medlife.deliveries.config.CustomUserTypes.StringArrayType;
import com.medlife.deliveries.utils.MapperUtils;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "consignment_subscriptions")
@TypeDef(name ="string-list",typeClass = StringArrayType.class)
@Data
public class ConsignmentSubscription extends BaseModel{
	
	public ConsignmentSubscription () {

	}

	@NotNull
	@Column(name = "consignment_id")
	private Long consignmentId;

	@NotBlank
	@Column(name = "endpoint")
	private String endpoint;

	@NotBlank
	@Column(name = "http_method")
	private String httpMethod;
	
	@Column(name = "headers")
	@Type(type = "jsonb")
	private JsonNode headers;
	
	@Column(name = "retries")
	private Long retries;

	@NotEmpty
	@Column(name = "subscribed_statuses")
	@Type(type="string-list")
	private List<@NotBlank String> subscribedStatuses;
	
	@Column(name = "response")
	@Type(type = "jsonb")
	private JsonNode response;

	@ApiModelProperty(hidden = true)
	@Column(name = "deleted_at")
	private Long deletedAt;
	
	@Column(name = "notified_at")
	private Long notifiedAt;

	public HttpHeaders getHeaders(){
		return JsonTransformer.convertToHttpHeaders(this.headers);
	}

	@PostLoad
	public void postLoad(){
		this.subscribedStatuses = MapperUtils.getClassTypeListFromHashMapType(this.subscribedStatuses, String.class);
	}

	@PostUpdate
	public void postUpdate(){
		this.subscribedStatuses = this.subscribedStatuses.stream().map( x -> MapperUtils.getDeepCopy(x,String.class)).collect(Collectors.toList());
	}
}
