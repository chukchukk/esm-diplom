package io.demo.entity;

import io.demo.entity.enums.Option;
import io.demo.entity.enums.RequestCategory;
import io.demo.entity.enums.RequestType;
import io.tesler.model.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "REQUEST_DATA_TYPE_OPTION")
@Getter
@Setter
@NoArgsConstructor
public class RequestDataTypeOption extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "REQUEST_TYPE")
	private RequestType requestType;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY")
	private RequestCategory category;

	@Column(name = "REQUIRED")
	private Boolean isRequired;

	@Column(name = "ACTIVE")
	private Boolean isActive;

	@Enumerated(EnumType.STRING)
	@Column(name = "OPTION")
	private Option option;

}
