package io.demo.entity.requestdatabytype.personaldatachange;

import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.SnilsChange;
import io.demo.entity.requestdatabytype.category.PersonalDataChangeCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.DOCUMENTS_CHANGE)
@NoArgsConstructor
public class DocumentsChangeRequestData extends PersonalDataChangeCategoryRequestData {

	@Column(name = "CHANGE_SNILS")
	@Enumerated(EnumType.STRING)
	private SnilsChange changeSnils;

	@Column(name = "MARRIAGE_NAME_CHANGE")
	private Boolean marriageNameChange;

	@Column(name = "SNILS_CHANGE_BY_SELF ")
	private Boolean snilsChangeBySelf;
}
