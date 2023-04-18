package io.demo.entity.enums;

import io.demo.dto.RequestReadDTO_;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static io.demo.entity.enums.RequestType.*;

@Getter
@AllArgsConstructor
public enum RequestTypeAvailableReadFields {

	EMPLOYMENT_CERTIFICATE_FIELDS(EMPLOYMENT_CERTIFICATE, List.of(
			RequestReadDTO_.numberOfCopies.getName(),
			RequestReadDTO_.whereReference.getName(),
			RequestReadDTO_.whereReferenceOther.getName(),
			RequestReadDTO_.travelDateFrom.getName(),
			RequestReadDTO_.travelDateTo.getName(),
			RequestReadDTO_.fullNameForeignPassport.getName(),
			RequestReadDTO_.showSalary.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName(),
			RequestReadDTO_.language.getName()
	)),
	NDFL_CERTIFICATE_FIELDS(NDFL_CERTIFICATE, List.of(
			RequestReadDTO_.numberOfCopies.getName(),
			RequestReadDTO_.periodFrom.getName(),
			RequestReadDTO_.periodTo.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName()
	)),
	WORK_BOOK_FIELDS(WORK_BOOK, List.of(
			RequestReadDTO_.numberOfCopies.getName(),
			RequestReadDTO_.whereReference.getName(),
			RequestReadDTO_.whereReferenceOther.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName()
	)),
	WORK_CONTRACT_FIELDS(WORK_CONTRACT, List.of(
			RequestReadDTO_.numberOfCopies.getName(),
			RequestReadDTO_.whereReference.getName(),
			RequestReadDTO_.whereReferenceOther.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName()
	)),
	LUMP_SUM_BIRTH_FIELDS(LUMP_SUM_BIRTH, List.of(
			RequestReadDTO_.nameOfChild.getName(),
			RequestReadDTO_.dateOfBirthChild.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName()
	)),
	LUMP_SUM_CHILDCARE_FIELDS(LUMP_SUM_CHILDCARE, List.of(
			RequestReadDTO_.nameOfChild.getName(),
			RequestReadDTO_.dateOfBirthChild.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName()
	)),
	MATERIAL_ASSISTANCE_FIELDS(MATERIAL_ASSISTANCE, List.of(
			RequestReadDTO_.nameOfChild.getName(),
			RequestReadDTO_.dateOfBirthChild.getName(),
			RequestReadDTO_.paperCopy.getName(),
			RequestReadDTO_.deliveryNeed.getName(),
			RequestReadDTO_.deliveryAddress.getName()
	)),
	PASSPORT_CHANGE_FIELDS(PASSPORT_CHANGE, List.of()),
	DOCUMENTS_CHANGE_FIELDS(DOCUMENTS_CHANGE, List.of(
			RequestReadDTO_.changeSnils.getName(),
			RequestReadDTO_.marriageNameChange.getName()
	)),
	REGISTRATION_CHANGE_FIELDS(REGISTRATION_CHANGE, List.of(
			RequestReadDTO_.actualResidence.getName(),
			RequestReadDTO_.region.getName(),
			RequestReadDTO_.district.getName(),
			RequestReadDTO_.locality.getName(),
			RequestReadDTO_.street.getName(),
			RequestReadDTO_.house.getName(),
			RequestReadDTO_.flat.getName()
	)),
	CHILD_BIRTH_FIELDS(CHILD_BIRTH, List.of()),
	PHONE_NUMBER_CHANGE_FIELDS(PHONE_NUMBER_CHANGE, List.of(
			RequestReadDTO_.newPhoneNumber.getName()
	));


	private final RequestType type;

	private final List<String> availableOptionalFields;

	public static RequestTypeAvailableReadFields getByRequestType(RequestType type) {
		return Arrays.stream(RequestTypeAvailableReadFields.values())
				.filter(o -> o.getType().equals(type))
				.findFirst()
				.orElse(null);
	}
}
