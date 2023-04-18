package io.demo.dto.requestdatadtobytype.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.dto.requestdatadtobytype.category.DocumentCategoryAbstractDTO;
import io.demo.entity.enums.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MaterialAssistanceDTO extends DocumentCategoryAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.NAME_OF_CHILD)
	private String nameOfChild;

	@JsonProperty(Option.FieldNameConstant.DATE_OF_BIRTH_CHILD)
	private LocalDate dateOfBirthChild;

}
