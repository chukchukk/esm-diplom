package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum RequestStatus {

	NEW("Новая", true, BgColor.BLUE),
	IN_PROGRESS("В работе", true, BgColor.ORANGE),
	NEED_INFO("Дозапрос информации", true, BgColor.YELLOW),
	COMPLETE("Выполнена", false, BgColor.GREEN),
	REJECTED("Отклонена", false, BgColor.BRIGHT_RED),
	CANCELLED("Отменена", false, BgColor.BRIGHT_RED);

	private final String value;

	private final boolean isActive;

	private final BgColor color;

	@JsonValue
	public String getValue() {
		return this.value;
	}

	public static List<RequestStatus> getStatusesByIsActive(Boolean active) {
		return Stream
				.of(RequestStatus.values())
				.filter(rs -> rs.isActive() == active)
				.collect(Collectors.toList());
	}

}
