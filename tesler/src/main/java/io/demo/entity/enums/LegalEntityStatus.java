package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SuppressWarnings("java:S115")
public enum LegalEntityStatus {

	Available("Доступно"),
	NotAvailableClosed("Недоступно - Закрыто");

	private final String value;

	@JsonValue
	public String getValue() {
		return this.value;
	}
}
