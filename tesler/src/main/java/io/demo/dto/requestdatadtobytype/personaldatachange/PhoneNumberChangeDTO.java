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
public class PhoneNumberChangeDTO extends PersonalDataChangeCategoryAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.NEW_PHONE_NUMBER)
	private String newPhoneNumber;

}
