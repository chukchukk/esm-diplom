package io.demo.service.common;

import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.*;
import static java.time.DayOfWeek.SATURDAY;

public class CalculatingDateService {

	public static LocalDate addDaysSkippingWeekends(LocalDate date, int daysToAdd) {
		LocalDate resultDate = date;
		int addedDays = 0;
		while (addedDays < daysToAdd) {
			resultDate = resultDate.plusDays(1);
			if (!List.of(SATURDAY, SUNDAY).contains(resultDate.getDayOfWeek())) {
				++addedDays;
			}
		}
		return resultDate;
	}

	public static LocalDate subtractDaysSkippingWeekends(LocalDate date, int daysToSubtract) {
		LocalDate resultDate = date;
		int subtractedDays = 0;
		while (subtractedDays < daysToSubtract) {
			resultDate = resultDate.minusDays(1);
			if (!List.of(SATURDAY, SUNDAY).contains(resultDate.getDayOfWeek())) {
				++subtractedDays;
			}
		}
		return resultDate;
	}

}
