package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.demo.dto.RequestReadDTO;
import io.demo.dto.requestdatadtobytype.RequestDataAbstractDTO;
import io.demo.dto.requestdatadtobytype.document.*;
import io.demo.dto.requestdatadtobytype.personaldatachange.*;
import io.demo.entity.KeyValueMarker;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.document.*;
import io.demo.entity.requestdatabytype.personaldatachange.*;
import io.demo.mapper.RequestDataMapper;
import io.demo.mapper.RequestReadDTOMapper;
import io.demo.repository.LegalEntityRepository;
import io.tesler.model.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RequestType implements KeyValueMarker {

	EMPLOYMENT_CERTIFICATE("Справка с места работы", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toEmploymentCertificateRequestData((EmploymentCertificateDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toEmploymentCertificateRequestDataDTO((EmploymentCertificateRequestData) data.getLeft(), data.getRight()),
			triple -> RequestDataMapper.INSTANCE.updateEmploymentCertificateRequestData((EmploymentCertificateDTO) triple.getLeft(), (EmploymentCertificateRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromEmploymentCertificateRequestData(pair.getLeft(), (EmploymentCertificateRequestData) pair.getRight())),
	NDFL_CERTIFICATE("Справка 2-НДФЛ", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toNDFLCertificateRequestData((NDFLCertificateDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toNDFLCertificateRequestDataDTO((NDFLCertificateRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updateNDFLCertificateRequestData((NDFLCertificateDTO) triple.getLeft(), (NDFLCertificateRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromNDFLCertificateRequestData(pair.getLeft(), (NDFLCertificateRequestData) pair.getRight())),
	WORK_BOOK("Копия трудовой книжки", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toWorkBookRequestData((WorkBookDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toWorkBookRequestDataDTO((WorkBookRequestData) data.getLeft(), data.getRight()),
			triple -> RequestDataMapper.INSTANCE.updateWorkBookRequestData((WorkBookDTO) triple.getLeft(), (WorkBookRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromWorkBookRequestData(pair.getLeft(), (WorkBookRequestData) pair.getRight())),
	WORK_CONTRACT("Копия трудового договора", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toWorkContractRequestData((WorkContractDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toWorkContractRequestDataDTO((WorkContractRequestData) data.getLeft(), data.getRight()),
			triple -> RequestDataMapper.INSTANCE.updateWorkContractRequestData((WorkContractDTO) triple.getLeft(), (WorkContractRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromWorkContractRequestData(pair.getLeft(), (WorkContractRequestData) pair.getRight())),
	LUMP_SUM_BIRTH("Справка о неполучении единовременного пособия при рождении ребёнка", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toLumpSumBirthRequestData((LumpSumBirthDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toLumpSumBirthRequestDataDTO((LumpSumBirthRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updateLumpSumBirthRequestData((LumpSumBirthDTO) triple.getLeft(), (LumpSumBirthRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromLumpSumBirthRequestData(pair.getLeft(), (LumpSumBirthRequestData) pair.getRight())),
	LUMP_SUM_CHILDCARE("Справка о неполучении пособия по уходу за ребёнком до 1.5 лет", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toLumpSumChildcareRequestData((LumpSumChildcareDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toLumpSumChildcareRequestDataDTO((LumpSumChildcareRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updateLumpSumChildcareRequestData((LumpSumChildcareDTO) triple.getLeft(), (LumpSumChildcareRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromLumpSumChildcareRequestData(pair.getLeft(), (LumpSumChildcareRequestData) pair.getRight())),
	MATERIAL_ASSISTANCE("Справка о неполучении материальной помощи", true, RequestCategory.DOCUMENT,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toMaterialAssistanceRequestData((MaterialAssistanceDTO) firstPair.getLeft(), firstPair.getRight(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toMaMaterialAssistanceRequestDataDTO((MaterialAssistanceRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updateMaterialAssistanceRequestData((MaterialAssistanceDTO) triple.getLeft(), (MaterialAssistanceRequestData) triple.getMiddle(), triple.getRight()),
			pair -> RequestReadDTOMapper.INSTANCE.fromMaterialAssistanceRequestData(pair.getLeft(), (MaterialAssistanceRequestData) pair.getRight())),
	PASSPORT_CHANGE("Смена паспорта", true, RequestCategory.PERSONAL_DATA_CHANGE,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toPassportChangeRequestData((PassportChangeDTO) firstPair.getLeft(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toPassportChangeDTO((PassportChangeRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updatePassportChangeRequestData((PassportChangeDTO) triple.getLeft(), (PassportChangeRequestData) triple.getMiddle()),
			pair -> RequestReadDTOMapper.INSTANCE.fromPassportChangeRequestData(pair.getLeft(), (PassportChangeRequestData) pair.getRight())),
	DOCUMENTS_CHANGE("Смена документов в связи с изменением ФИО", true, RequestCategory.PERSONAL_DATA_CHANGE,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toDocumentsChangeRequestData((DocumentsChangeDTO) firstPair.getLeft(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toDocumentsChangeDTO((DocumentsChangeRequestData) data.getLeft(), data.getRight()),
			triple -> RequestDataMapper.INSTANCE.updateDocumentsChangeRequestData((DocumentsChangeDTO) triple.getLeft(), (DocumentsChangeRequestData) triple.getMiddle()),
			pair -> RequestReadDTOMapper.INSTANCE.fromDocumentsChangeRequestData(pair.getLeft(), (DocumentsChangeRequestData) pair.getRight())),
	REGISTRATION_CHANGE("Изменение прописки", true, RequestCategory.PERSONAL_DATA_CHANGE,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toRegistrationChangeRequestData((RegistrationChangeDTO) firstPair.getLeft(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toRegistrationChangeDTO((RegistrationChangeRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updateRegistrationChangeRequestData((RegistrationChangeDTO) triple.getLeft(), (RegistrationChangeRequestData) triple.getMiddle()),
			pair -> RequestReadDTOMapper.INSTANCE.fromRegistrationChangeRequestData(pair.getLeft(), (RegistrationChangeRequestData) pair.getRight())),
	PHONE_NUMBER_CHANGE("Изменение номера телефона", true, RequestCategory.PERSONAL_DATA_CHANGE,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toPhoneNumberChangeRequestData((PhoneNumberChangeDTO) firstPair.getLeft(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toPhoneNumberChangeDTO((PhoneNumberChangeRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updatePhoneNumberChangeRequestData((PhoneNumberChangeDTO) triple.getLeft(), (PhoneNumberChangeRequestData) triple.getMiddle()),
			pair -> RequestReadDTOMapper.INSTANCE.fromPhoneNumberChangeRequestData(pair.getLeft(), (PhoneNumberChangeRequestData) pair.getRight())),
	CHILD_BIRTH("Рождение ребёнка", true, RequestCategory.PERSONAL_DATA_CHANGE,
			(firstPair, user) -> RequestDataMapper.INSTANCE.toChildBirthRequestData((ChildBirthDTO) firstPair.getLeft(), user, RequestStatus.NEW),
			data -> RequestDataMapper.INSTANCE.toChildBirthDTO((ChildBirthRequestData) data.getLeft()),
			triple -> RequestDataMapper.INSTANCE.updateChildBirthRequestData((ChildBirthDTO) triple.getLeft(), (ChildBirthRequestData) triple.getMiddle()),
			pair -> RequestReadDTOMapper.INSTANCE.fromChildBirthRequestData(pair.getLeft(), (ChildBirthRequestData) pair.getRight()));

	@JsonValue
	private final String value;

	private final Boolean isInternal;

	private final RequestCategory category;

	@JsonIgnore
	private final BiFunction<Pair<RequestDataAbstractDTO, LegalEntityRepository>, User, RequestData> mapRequestDataAbstractDTOToNewRequestData;

	@JsonIgnore
	private final Function<Pair<RequestData, Boolean>, RequestDataAbstractDTO> mapRequestDataToDTO;

	@JsonIgnore
	private final Consumer<Triple<RequestDataAbstractDTO, RequestData, LegalEntityRepository>> updateExistingRequestData;

	@JsonIgnore
	private final Consumer<Pair<RequestReadDTO, RequestData>> fillRequestReadDTOFields;

	@Override
	public String getKey() {
		return name();
	}

	public static final class RequestTypeKeyConstant {
		public static final String EMPLOYMENT_CERTIFICATE = "EMPLOYMENT_CERTIFICATE";
		public static final String NDFL_CERTIFICATE = "NDFL_CERTIFICATE";
		public static final String WORK_BOOK = "WORK_BOOK";
		public static final String WORK_CONTRACT = "WORK_CONTRACT";
		public static final String LUMP_SUM_BIRTH = "LUMP_SUM_BIRTH";
		public static final String LUMP_SUM_CHILDCARE = "LUMP_SUM_CHILDCARE";
		public static final String MATERIAL_ASSISTANCE = "MATERIAL_ASSISTANCE";
		public static final String INTERNAL_CORRESPONDENCE = "INTERNAL_CORRESPONDENCE";
		public static final String PASSPORT_CHANGE = "PASSPORT_CHANGE";
		public static final String DOCUMENTS_CHANGE = "DOCUMENTS_CHANGE";
		public static final String REGISTRATION_CHANGE = "REGISTRATION_CHANGE";
		public static final String PHONE_NUMBER_CHANGE = "PHONE_NUMBER_CHANGE";
		public static final String CHILD_BIRTH = "CHILD_BIRTH";
		public static final String ACCESS_TO_1C_SED_SELF = "ACCESS_TO_1C_SED_SELF";
		public static final String ACCESS_TO_1C_SED_FOR_EMPLOYEE = "ACCESS_TO_1C_SED_FOR_EMPLOYEE";
	}

	public static Optional<RequestType> getByTitle(@NonNull String title) {
		return Arrays.stream(RequestType.values())
				.filter(type -> type.getValue().equals(title))
				.findFirst();
	}

	public static List<KeyValueMarker> getAllKeyMarkersByCategory(RequestCategory category) {
		return Arrays.stream(RequestType.values())
				.filter(requestType -> category.equals(requestType.getCategory()))
				.collect(Collectors.toList());
	}

	public static List<RequestType> getAllByCategory(RequestCategory category) {
		return Arrays.stream(RequestType.values())
				.filter(requestType -> category.equals(requestType.getCategory()))
				.collect(Collectors.toList());
	}

	private static Optional<RequestType> getByName(String name) {
		return Arrays.stream(RequestType.values())
				.filter(rt -> rt.name().equals(name))
				.findFirst();
	}

	@JsonCreator
	private static RequestType deserialize(String name) {
		return getByTitle(name).orElseGet(
				() -> getByName(name).orElseThrow()
		);
	}

}
