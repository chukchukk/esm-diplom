package io.demo.dto;

import io.demo.entity.enums.RequestStatus;
import io.demo.entity.enums.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RequestDataMainInfoDTO {

	private long requestId;

	private LocalDate createdDate;

	private RequestType requestType;

	private RequestStatus status;

	private String legalEntityName;

}
