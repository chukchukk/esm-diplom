package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public enum PortalFieldType {

	SELECT("Select"),
	INTEGER(Integer.class.getSimpleName()),
	BOOLEAN(Boolean.class.getSimpleName()),
	STRING(String.class.getSimpleName()),
	DATE(Date.class.getSimpleName()),
	LOCAL_DATE_TIME(LocalDateTime.class.getSimpleName()),
	CHECKBOX("Checkbox"),
	FILE("File");

	@JsonValue
	private final String value;

}
