package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.demo.entity.KeyValueMarker;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.demo.entity.enums.RequestType.*;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum WhereReference implements KeyValueMarker {

	BANK("В банк", List.of(EMPLOYMENT_CERTIFICATE, WORK_BOOK, WORK_CONTRACT)),
	PERSONAL_USE("В личных целях", List.of(WORK_CONTRACT)),
	PENSION_FUND("В пенсионный фонд", List.of(WORK_BOOK)),
	OTHER_COMPANY("В другую компанию ", List.of(WORK_BOOK)),
	EMBASSY("В посольство", List.of(EMPLOYMENT_CERTIFICATE)),
	OTHER("Иное", List.of(EMPLOYMENT_CERTIFICATE, WORK_BOOK, WORK_CONTRACT));

	@JsonValue
	private final String value;

	private final List<RequestType> requestTypes;

	@Override
	public String getKey() {
		return this.name();
	}

	public static List<KeyValueMarker> getKeyMarkersByRequestType(RequestType type) {
		return Arrays.stream(WhereReference.values())
				.filter(wr -> wr.getRequestTypes().contains(type))
				.collect(Collectors.toList());
	}

	public static List<WhereReference> getWhereReferenceByRequestType(RequestType type) {
		return Arrays.stream(WhereReference.values())
				.filter(wr -> wr.getRequestTypes().contains(type))
				.collect(Collectors.toList());
	}
}