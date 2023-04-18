package io.demo.dto;

import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.model.core.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImplementerDTO extends DataResponseDTO {

	@SearchParameter(name = "fullUserName")
	private String implementerName;

	public ImplementerDTO(User user) {
		this.id = user.getId().toString();
		this.implementerName = user.getFullName();
	}
}
