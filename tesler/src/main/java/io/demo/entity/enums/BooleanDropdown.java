package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BooleanDropdown {

	YES("Да", true),
	NO("Нет", false);

	@JsonValue
	private final String uiValue;

	private final Boolean boolValue;

	public static BooleanDropdown getByBoolValue(Boolean boolValue) {
		return Arrays.stream(BooleanDropdown.values())
				.filter(bd -> bd.getBoolValue().equals(boolValue))
				.findFirst()
				.orElse(null);
	}

}
