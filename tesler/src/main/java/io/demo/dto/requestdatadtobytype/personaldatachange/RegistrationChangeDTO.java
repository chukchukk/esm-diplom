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
public class RegistrationChangeDTO extends PersonalDataChangeCategoryAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.ACTUAL_RESIDENCE)
	private Boolean actualResidence;

	@JsonProperty(Option.FieldNameConstant.REGION)
	private String region;

	@JsonProperty(Option.FieldNameConstant.DISTRICT)
	private String district;

	@JsonProperty(Option.FieldNameConstant.LOCALITY)
	private String locality;

	@JsonProperty(Option.FieldNameConstant.STREET)
	private String street;

	@JsonProperty(Option.FieldNameConstant.HOUSE)
	private String house;

	@JsonProperty(Option.FieldNameConstant.FLAT)
	private String flat;

}
