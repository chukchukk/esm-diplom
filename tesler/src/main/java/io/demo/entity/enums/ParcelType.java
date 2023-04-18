package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.demo.entity.KeyValueMarker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ParcelType implements KeyValueMarker {

	CARGO("Груз"),
	DOCUMENT("Документ");

	private final String value;

	@Override
	public String getKey() {
		return name();
	}
}
