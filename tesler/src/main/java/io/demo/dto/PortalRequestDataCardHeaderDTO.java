package io.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.entity.enums.Option;
import io.demo.entity.enums.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PortalRequestDataCardHeaderDTO {

	private LocalDate createdDate;

	private String legalEntityName;

	@JsonProperty(Option.FieldNameConstant.TYPE)
	private String type;

	private RequestStatus status;

}
