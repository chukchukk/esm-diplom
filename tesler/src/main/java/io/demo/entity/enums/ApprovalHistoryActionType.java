package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApprovalHistoryActionType {

	IMPLEMENTER_CHANGE("Изменился исполнитель"),
	STATUS_CHANGE("Изменился статус"),
	SOLUTION_DEADLINE_CHANGE("Изменен срок исполнения"),
	COMMENT_ADD("Добавлен комментарий"),
	FILES_ADD("Добавлены файлы");

	private final String value;

	@JsonValue
	public String getValue() {
		return this.value;
	}

}
