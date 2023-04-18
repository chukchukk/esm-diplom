package io.demo.dto.requestdatadtobytype.personaldatachange;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.dto.requestdatadtobytype.category.PersonalDataChangeCategoryAbstractDTO;
import io.demo.entity.enums.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DocumentsChangeDTO extends PersonalDataChangeCategoryAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.CHANGE_SNILS)
	private String changeSnils;

	@JsonProperty(Option.FieldNameConstant.MARRIAGE_NAME_CHANGE)
	private Boolean marriageNameChange;

}
