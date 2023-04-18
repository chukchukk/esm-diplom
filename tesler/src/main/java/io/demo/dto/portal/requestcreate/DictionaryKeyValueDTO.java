package io.demo.dto.portal.requestcreate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DictionaryKeyValueDTO {

	private String key;

	private String value;

	public DictionaryKeyValueDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}

}
