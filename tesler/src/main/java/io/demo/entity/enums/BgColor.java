package io.demo.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BgColor {

	BLUE("#01A5E1"),
	ORANGE("#FCA546"),
	GREEN("#008C3E"),
	BRIGHT_RED("#F5222D"),
	PALE_RED("#F08080"),
	YELLOW("#FCDD76");

	private final String value;

}
