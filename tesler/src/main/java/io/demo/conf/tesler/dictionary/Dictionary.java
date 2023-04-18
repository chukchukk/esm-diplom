package io.demo.conf.tesler.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;
import io.tesler.api.data.dictionary.LOV;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Dictionary {

	@RequiredArgsConstructor
	@Getter
	public enum UserRoleEnum {
		EMPLOYEE(new LOV("EMPLOYEE"), "Работник"),
		ADMIN(new LOV("ADMIN"), "Администратор"),
		IMPLEMENTER(new LOV("IMPLEMENTER"), "Исполнитель"),
		HEAD_IMPLEMENTER(new LOV("HEAD_IMPLEMENTER"), "Руководитель группы исполнителей");

		private final LOV lov;

		@JsonValue
		private final String value;
	}

}