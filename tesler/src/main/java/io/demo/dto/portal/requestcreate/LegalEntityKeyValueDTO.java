package io.demo.dto.portal.requestcreate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@NoArgsConstructor
public class LegalEntityKeyValueDTO extends DictionaryKeyValueDTO {

	private Boolean main;

	public LegalEntityKeyValueDTO(String key, String value, Boolean isDefault) {
		super(key, value);
		this.main = ofNullable(isDefault).orElse(false);
	}

}
