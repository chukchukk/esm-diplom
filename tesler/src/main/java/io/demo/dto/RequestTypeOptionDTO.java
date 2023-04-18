package io.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.demo.entity.enums.Option;
import io.demo.entity.enums.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RequestTypeOptionDTO {

	@JsonIgnore
	private RequestType requestType;

	private Boolean isRequired;

	private Option option;

}
