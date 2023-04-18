package io.demo.mapper;

import io.demo.dto.RequestReadDTO;
import io.demo.entity.enums.Language;
import io.demo.entity.requestdatabytype.document.*;
import io.demo.entity.requestdatabytype.personaldatachange.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.Optional;

@Mapper(imports = {Optional.class, Language.class})
public interface RequestReadDTOMapper {

	RequestReadDTOMapper INSTANCE = Mappers.getMapper(RequestReadDTOMapper.class);

	String yesString = "Да";

	String noString = "Нет";

	/**
	 * DocumentCategory
	 */

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "numberOfCopies", expression = "java(data.getNumberOfCopies())")
	@Mapping(target = "whereReference", expression = "java(data.getWhereReference().getValue())")
	@Mapping(target = "whereReferenceOther", expression = "java(data.getWhereReferenceOther())")
	@Mapping(target = "travelDateFrom", expression = "java(data.getTravelDateFrom())")
	@Mapping(target = "travelDateTo", expression = "java(data.getTravelDateTo())")
	@Mapping(target = "fullNameForeignPassport", expression = "java(data.getFullNameForeignPassport())")
	@Mapping(target = "showSalary", expression = "java(data.getShowSalary() ? yesString : noString)")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "language", expression = "java(Optional.ofNullable(data.getLanguage()).map(Language::getValue).orElse(null))")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromEmploymentCertificateRequestData(@MappingTarget RequestReadDTO dto, EmploymentCertificateRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "nameOfChild", expression = "java(data.getNameOfChild())")
	@Mapping(target = "dateOfBirthChild", expression = "java(data.getDateOfBirthChild())")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromLumpSumBirthRequestData(@MappingTarget RequestReadDTO dto, LumpSumBirthRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "nameOfChild", expression = "java(data.getNameOfChild())")
	@Mapping(target = "dateOfBirthChild", expression = "java(data.getDateOfBirthChild())")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromLumpSumChildcareRequestData(@MappingTarget RequestReadDTO dto, LumpSumChildcareRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "nameOfChild", expression = "java(data.getNameOfChild())")
	@Mapping(target = "dateOfBirthChild", expression = "java(data.getDateOfBirthChild())")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromMaterialAssistanceRequestData(@MappingTarget RequestReadDTO dto, MaterialAssistanceRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "numberOfCopies", expression = "java(data.getNumberOfCopies())")
	@Mapping(target = "periodFrom", expression = "java(data.getPeriodFrom())")
	@Mapping(target = "periodTo", expression = "java(data.getPeriodTo())")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromNDFLCertificateRequestData(@MappingTarget RequestReadDTO dto, NDFLCertificateRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "numberOfCopies", expression = "java(data.getNumberOfCopies())")
	@Mapping(target = "whereReference", expression = "java(data.getWhereReference().getValue())")
	@Mapping(target = "whereReferenceOther", expression = "java(data.getWhereReferenceOther())")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromWorkBookRequestData(@MappingTarget RequestReadDTO dto, WorkBookRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "numberOfCopies", expression = "java(data.getNumberOfCopies())")
	@Mapping(target = "whereReference", expression = "java(data.getWhereReference().getValue())")
	@Mapping(target = "whereReferenceOther", expression = "java(data.getWhereReferenceOther())")
	@Mapping(target = "deliveryNeed", expression = "java(data.getDeliveryNeed() ? yesString : noString)")
	@Mapping(target = "deliveryAddress", expression = "java(data.getDeliveryAddress())")
	@Mapping(target = "paperCopy", expression = "java(data.getPaperCopy() ? yesString : noString)")
	void fromWorkContractRequestData(@MappingTarget RequestReadDTO dto, WorkContractRequestData data);

	/**
	 * PersonalDataChangeCategory
	 */

	@BeanMapping(ignoreByDefault = true)
	void fromPassportChangeRequestData(@MappingTarget RequestReadDTO dto, PassportChangeRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "changeSnils", expression = "java(data.getChangeSnils().getValue())")
	@Mapping(target = "marriageNameChange", expression = "java(data.getMarriageNameChange() ? yesString : noString)")
	void fromDocumentsChangeRequestData(@MappingTarget RequestReadDTO dto, DocumentsChangeRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "actualResidence", expression = "java(data.getActualResidence() ? yesString : noString)")
	@Mapping(target = "region", expression = "java(data.getRegion())")
	@Mapping(target = "district", expression = "java(data.getDistrict())")
	@Mapping(target = "locality", expression = "java(data.getLocality())")
	@Mapping(target = "street", expression = "java(data.getStreet())")
	@Mapping(target = "house", expression = "java(data.getHouse())")
	@Mapping(target = "flat", expression = "java(data.getFlat())")
	void fromRegistrationChangeRequestData(@MappingTarget RequestReadDTO dto, RegistrationChangeRequestData data);

	@BeanMapping(ignoreByDefault = true)
	void fromChildBirthRequestData(@MappingTarget RequestReadDTO dto, ChildBirthRequestData data);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "newPhoneNumber", expression = "java(data.getNewPhoneNumber())")
	void fromPhoneNumberChangeRequestData(@MappingTarget RequestReadDTO dto, PhoneNumberChangeRequestData data);

}
