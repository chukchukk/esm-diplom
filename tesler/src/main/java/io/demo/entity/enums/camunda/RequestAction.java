package io.demo.entity.enums.camunda;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestAction {

	TAKE_TO_WORK("Взять в работу"),
	CANCEL("Отменить"),
	REJECT("Отклонить"),
	EXECUTE("Выполнить"),
	CHANGE_IMPLEMENTER("Изменить выполняющего"),
	CALL_FOR_EXTRA_INFO("Дозапрос информации"),
	SEND_TO_WORK("Отправить в работу");

	private final String value;

}
