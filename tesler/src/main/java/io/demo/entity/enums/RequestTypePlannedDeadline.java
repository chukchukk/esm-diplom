package io.demo.entity.enums;

import io.demo.entity.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RequestTypePlannedDeadline {

	DEFAULT(null,  3),
	EMPLOYMENT_CERTIFICATE_DEADLINE(RequestType.EMPLOYMENT_CERTIFICATE, 3),
	NDFL_CERTIFICATE_DEADLINE(RequestType.NDFL_CERTIFICATE, 3),
	WORK_BOOK_DEADLINE(RequestType.WORK_BOOK, 3),
	WORK_CONTRACT_DEADLINE(RequestType.WORK_CONTRACT, 3),
	LUMP_SUM_BIRTH_DEADLINE(RequestType.LUMP_SUM_BIRTH, 3),
	LUMP_SUM_CHILDCARE_DEADLINE(RequestType.LUMP_SUM_CHILDCARE, 3),
	MATERIAL_ASSISTANCE_DEADLINE(RequestType.MATERIAL_ASSISTANCE, 3),
	PASSPORT_CHANGE(RequestType.PASSPORT_CHANGE, 3),
	DOCUMENTS_CHANGE(RequestType.DOCUMENTS_CHANGE, 3),
	REGISTRATION_CHANGE(RequestType.REGISTRATION_CHANGE, 3),
	CHILD_BIRTH(RequestType.CHILD_BIRTH, 3),
	PHONE_NUMBER_CHANGE(RequestType.PHONE_NUMBER_CHANGE, 3);

	private final RequestType requestType;

	private final Integer plusDays;

	public static Integer getPlusDaysByRequestType(RequestType type) {
		return Arrays.stream(RequestTypePlannedDeadline.values())
				.filter(o -> type.equals(o.getRequestType()))
				.findFirst()
				.map(RequestTypePlannedDeadline::getPlusDays)
				.orElse(null);
	}
}
