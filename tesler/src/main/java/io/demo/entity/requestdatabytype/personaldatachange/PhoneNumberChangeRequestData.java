package io.demo.entity.requestdatabytype.personaldatachange;

import io.demo.entity.enums.RequestType;
import io.demo.entity.requestdatabytype.category.PersonalDataChangeCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.PHONE_NUMBER_CHANGE)
@NoArgsConstructor
public class PhoneNumberChangeRequestData extends PersonalDataChangeCategoryRequestData {

	@Column(name = "NEW_PHONE_NUMBER")
	private String newPhoneNumber;
}
