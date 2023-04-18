package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.demo.entity.KeyValueMarker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeliveryType implements KeyValueMarker {

	PICK_UP_FROM_CLIENT("Забрать у клиента"),
	SEND_TO_CLIENT("Отправить клиенту"),
	SEND_AND_PICK_UP("Отправить и забрать");

	private final String value;

	@Override
	public String getKey() {
		return name();
	}
}
