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
public class EmployeeDTO extends DataResponseDTO {

	@SearchParameter(name = "fullUserName")
	private String employeeName;

	public EmployeeDTO(User user) {
		this.id = user.getId().toString();
		this.employeeName = user.getFullName();
	}

}
