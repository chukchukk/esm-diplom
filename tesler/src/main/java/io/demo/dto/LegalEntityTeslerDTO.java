package io.demo.dto;

import io.demo.entity.LegalEntity;
import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.util.filter.SearchParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LegalEntityTeslerDTO extends DataResponseDTO {

	@SearchParameter(name = "fullName")
	private String legalEntityName;

	public LegalEntityTeslerDTO(LegalEntity legalEntity) {
		this.id = legalEntity.getId().toString();
		this.legalEntityName = legalEntity.getFullName();
	}

}
