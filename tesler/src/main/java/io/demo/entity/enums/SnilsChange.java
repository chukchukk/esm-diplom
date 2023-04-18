package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.demo.entity.KeyValueMarker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum SnilsChange implements KeyValueMarker {

	INDEPENDENTLY("Самостоятельно"),
	THROUGH_HR("Через отдел кадров");

	@JsonValue
	private final String value;

	@Override
	public String getKey() {
		return name();
	}

}
