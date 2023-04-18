package io.demo.mapper;

import io.demo.dto.RequestDataMainInfoDTO;
import io.demo.dto.requestdatadtobytype.RequestDataAbstractDTO;
import io.demo.dto.requestdatadtobytype.document.*;
import io.demo.dto.requestdatadtobytype.personaldatachange.*;
import io.demo.entity.LegalEntity;
import io.demo.entity.enums.RequestStatus;
import io.demo.entity.enums.RequestTypePlannedDeadline;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.document.*;
import io.demo.entity.requestdatabytype.personaldatachange.*;
import io.demo.repository.LegalEntityRepository;
import io.demo.service.common.CalculatingDateService;
import io.tesler.model.core.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Optional;

@Mapper(imports = {Optional.class, LocalDate.class, RequestTypePlannedDeadline.class, CalculatingDateService.class,
		LegalEntity.class, RequestDataAbstractDTO.class, RequestDataAbstractDTO.RequestPortalType.class })
public interface RequestDataMapper {

	RequestDataMapper INSTANCE = Mappers.getMapper(RequestDataMapper.class);

	String noLegalEntityText = "Не требуется";

	@Mapping(target = "requestId", expression = "java(data.getId())")
	@Mapping(target = "createdDate", expression = "java(data.getCreatedDate().toLocalDate())")
	@Mapping(target = "requestType", expression = "java(data.getType())")
	@Mapping(target = "legalEntityName", expression = "java(Optional.ofNullable(data.getLegalEntity()).map(LegalEntity::getFullName).orElse(RequestDataMapper.noLegalEntityText))")
	RequestDataMainInfoDTO toRequestDataMainInfoDTO(RequestData data);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	MaterialAssistanceRequestData toMaterialAssistanceRequestData(MaterialAssistanceDTO dto,
																	@Context LegalEntityRepository legalEntityRepository,
																	@Context User user,
																	@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	LumpSumChildcareRequestData toLumpSumChildcareRequestData(LumpSumChildcareDTO dto,
																@Context LegalEntityRepository legalEntityRepository,
																@Context User user,
																@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	LumpSumBirthRequestData toLumpSumBirthRequestData(LumpSumBirthDTO dto,
														@Context LegalEntityRepository legalEntityRepository,
														@Context User user,
														@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	WorkContractRequestData toWorkContractRequestData(WorkContractDTO dto,
														@Context LegalEntityRepository legalEntityRepository,
														@Context User user,
														@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	WorkBookRequestData toWorkBookRequestData(WorkBookDTO dto,
												@Context LegalEntityRepository legalEntityRepository,
												@Context User user,
												@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	NDFLCertificateRequestData toNDFLCertificateRequestData(NDFLCertificateDTO dto,
															@Context LegalEntityRepository legalEntityRepository,
															@Context User user,
															@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	EmploymentCertificateRequestData toEmploymentCertificateRequestData(EmploymentCertificateDTO dto,
																		@Context LegalEntityRepository legalEntityRepository,
																		@Context User user,
																		@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	PassportChangeRequestData toPassportChangeRequestData(PassportChangeDTO dto,
															@Context User user,
															@Context RequestStatus status);


	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "marriageNameChange", expression = "java(Optional.ofNullable(dto.getMarriageNameChange()).orElse(false))")
	DocumentsChangeRequestData toDocumentsChangeRequestData(DocumentsChangeDTO dto,
															@Context User user,
															@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	RegistrationChangeRequestData toRegistrationChangeRequestData(RegistrationChangeDTO dto,
																	@Context User user,
																	@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	ChildBirthRequestData toChildBirthRequestData(ChildBirthDTO dto,
													@Context User user,
													@Context RequestStatus status);

	@Mapping(target = "type", expression = "java(dto.getPortalType().getRequestType())")
	@Mapping(target = "category", expression = "java(dto.getPortalType().getRequestType().getCategory())")
	@Mapping(target = "user", expression = "java(user)")
	@Mapping(target = "author", expression = "java(user)")
	@Mapping(target = "status", expression = "java(status)")
	@Mapping(target = "plannedDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	@Mapping(target = "actualDeadline", expression = "java(CalculatingDateService.addDaysSkippingWeekends(LocalDate.now(), RequestTypePlannedDeadline.getPlusDaysByRequestType(dto.getPortalType().getRequestType())))")
	PhoneNumberChangeRequestData toPhoneNumberChangeRequestData(PhoneNumberChangeDTO dto,
																@Context User user,
																@Context RequestStatus status);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	MaterialAssistanceDTO toMaMaterialAssistanceRequestDataDTO(MaterialAssistanceRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	LumpSumChildcareDTO toLumpSumChildcareRequestDataDTO(LumpSumChildcareRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	LumpSumBirthDTO toLumpSumBirthRequestDataDTO(LumpSumBirthRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	@Mapping(target = "whereReference", expression = "java(isKeyNeeded ? data.getWhereReference().getKey() : data.getWhereReference().getValue())")
	WorkContractDTO toWorkContractRequestDataDTO(WorkContractRequestData data, @Context Boolean isKeyNeeded);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	@Mapping(target = "whereReference", expression = "java(isKeyNeeded ? data.getWhereReference().getKey() : data.getWhereReference().getValue())")
	WorkBookDTO toWorkBookRequestDataDTO(WorkBookRequestData data, @Context Boolean isKeyNeeded);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	NDFLCertificateDTO toNDFLCertificateRequestDataDTO(NDFLCertificateRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	@Mapping(target = "language", expression = "java(isKeyNeeded ? data.getLanguage().getKey() : data.getLanguage().getValue())")
	@Mapping(target = "whereReference", expression = "java(isKeyNeeded ? data.getWhereReference().getKey() : data.getWhereReference().getValue())")
	EmploymentCertificateDTO toEmploymentCertificateRequestDataDTO(EmploymentCertificateRequestData data, @Context Boolean isKeyNeeded);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	PassportChangeDTO toPassportChangeDTO(PassportChangeRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	@Mapping(target = "changeSnils", expression = "java(isKeyNeeded ? data.getChangeSnils().getKey() : data.getChangeSnils().getValue())")
	DocumentsChangeDTO toDocumentsChangeDTO(DocumentsChangeRequestData data, @Context Boolean isKeyNeeded);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	RegistrationChangeDTO toRegistrationChangeDTO(RegistrationChangeRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	ChildBirthDTO toChildBirthDTO(ChildBirthRequestData data);

	@Mapping(target = "comment", ignore = true)
	@Mapping(target = "portalType", expression = "java(RequestDataAbstractDTO.RequestPortalType.getByRequestType(data.getType()))")
	PhoneNumberChangeDTO toPhoneNumberChangeDTO(PhoneNumberChangeRequestData data);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateMaterialAssistanceRequestData(MaterialAssistanceDTO dto,
												@MappingTarget MaterialAssistanceRequestData data,
												@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateLumpSumChildcareRequestData(LumpSumChildcareDTO dto,
											@MappingTarget LumpSumChildcareRequestData data,
											@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateLumpSumBirthRequestData(LumpSumBirthDTO dto,
										@MappingTarget LumpSumBirthRequestData data,
										@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateWorkContractRequestData(WorkContractDTO dto,
										@MappingTarget WorkContractRequestData data,
										@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateWorkBookRequestData(WorkBookDTO dto,
									@MappingTarget WorkBookRequestData data,
									@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateNDFLCertificateRequestData(NDFLCertificateDTO dto,
											@MappingTarget NDFLCertificateRequestData data,
											@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	@Mapping(target = "legalEntity", expression = "java(legalEntityRepository.getById(Long.valueOf(dto.getLegalEntityId())))")
	@Mapping(target = "deliveryNeed", expression = "java(Optional.ofNullable(dto.getDeliveryNeed()).orElse(false))")
	void updateEmploymentCertificateRequestData(EmploymentCertificateDTO dto,
												@MappingTarget EmploymentCertificateRequestData data,
												@Context LegalEntityRepository legalEntityRepository);

	@Mapping(target = "type", ignore = true)
	void updatePassportChangeRequestData(PassportChangeDTO dto, @MappingTarget PassportChangeRequestData data);

	@Mapping(target = "type", ignore = true)
	void updateDocumentsChangeRequestData(DocumentsChangeDTO dto, @MappingTarget DocumentsChangeRequestData data);

	@Mapping(target = "type", ignore = true)
	void updateRegistrationChangeRequestData(RegistrationChangeDTO dto, @MappingTarget RegistrationChangeRequestData data);

	@Mapping(target = "type", ignore = true)
	void updateChildBirthRequestData(ChildBirthDTO dto, @MappingTarget ChildBirthRequestData data);

	@Mapping(target = "type", ignore = true)
	void updatePhoneNumberChangeRequestData(PhoneNumberChangeDTO dto, @MappingTarget PhoneNumberChangeRequestData data);

}
