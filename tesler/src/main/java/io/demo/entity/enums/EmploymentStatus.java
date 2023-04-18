package io.demo.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmploymentStatus {

	WORKS("Работает"),
	FIRED("Уволен");

	private final String value;

}
