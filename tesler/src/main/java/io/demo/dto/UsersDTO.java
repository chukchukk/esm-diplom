package io.demo.dto;

import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.model.core.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class UsersDTO extends DataResponseDTO {

	private String readRoles;

	@SearchParameter(name = "fullUserName")
	private String fullName;

	@SearchParameter(name = "email")
	private String email;

	private LocalDateTime createdDate;

	public UsersDTO(User user) {
		this.id = user.getId().toString();
		this.fullName = Optional.of(user).map(User::getFullUserName).orElse(null);
		this.email = Optional.of(user).map(User::getEmail).orElse(null);
		this.createdDate = Optional.of(user).map(User::getCreatedDate).orElse(null);
	}
}
