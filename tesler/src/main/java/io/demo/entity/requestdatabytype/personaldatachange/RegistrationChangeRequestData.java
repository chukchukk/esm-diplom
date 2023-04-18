package io.demo.entity.requestdatabytype.personaldatachange;

import io.demo.entity.enums.RequestType;
import io.demo.entity.requestdatabytype.category.PersonalDataChangeCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.REGISTRATION_CHANGE)
@NoArgsConstructor
public class RegistrationChangeRequestData extends PersonalDataChangeCategoryRequestData {

	@Column(name = "ACTUAL_RESIDENCE")
	private Boolean actualResidence;

	@Column(name = "REGION")
	private String region;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "LOCALITY")
	private String locality;

	@Column(name = "STREET")
	private String street;

	@Column(name = "HOUSE")
	private String house;

	@Column(name = "FLAT")
	private String flat;

}
