package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.demo.dto.RequestFormPortalDTO;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import io.demo.entity.requestdatabytype.document.EmploymentCertificateRequestData;
import io.demo.entity.requestdatabytype.personaldatachange.RegistrationChangeRequestData;
import io.demo.mapper.PortalFieldVisibilityMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Consumer;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum Option {

	COMPANY_NAME(FieldNameConstant.USER_COMPANY_NAME, "Название юридического лица", PortalFieldType.STRING, false, false, null),
	TYPE(FieldNameConstant.TYPE, "Тип заявки", PortalFieldType.STRING, false, false, null),
	COMPANY_ID(FieldNameConstant.USER_COMPANY, "Юридическое лицо", PortalFieldType.SELECT, false, false, null),
	NUMBER_OF_COPIES(FieldNameConstant.NUMBER_OF_COPIES, "Количество экземпляров", PortalFieldType.INTEGER, false, true, null),
	SHOW_SALARY(FieldNameConstant.SHOW_SALARY, "Указывать зарплату", PortalFieldType.BOOLEAN, false, true, null),
	WHERE_REFERENCE(FieldNameConstant.WHERE_REFERENCE, "Место предоставления", PortalFieldType.SELECT, false, true, null),
	WHERE_REFERENCE_OTHER(FieldNameConstant.WHERE_REFERENCE_OTHER, "Иное место предоставления", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateWhereReferenceOtherVisibility(pair.getLeft(), pair.getRight())),
	TRAVEL_DATE_FROM(FieldNameConstant.TRAVEL_DATE_FROM, "Дата начала поездки", PortalFieldType.DATE, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateTravelFieldVisibility((EmploymentCertificateRequestData) pair.getLeft(), pair.getRight())),
	TRAVEL_DATE_TO(FieldNameConstant.TRAVEL_DATE_TO, "Дата окончания поездки", PortalFieldType.DATE, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateTravelFieldVisibility((EmploymentCertificateRequestData) pair.getLeft(), pair.getRight())),
	FULL_NAME_FOREIGN_PASSPORT(FieldNameConstant.FULL_NAME_FOREIGN_PASSPORT, "Фамилия и имя из заграничного паспорта", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateTravelFieldVisibility((EmploymentCertificateRequestData) pair.getLeft(), pair.getRight())),
	DELIVERY_NEED(FieldNameConstant.DELIVERY_NEED, "Требуется доставка", PortalFieldType.BOOLEAN, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateDeliveryNeedVisibility((DocumentCategoryRequestData) pair.getLeft(), pair.getRight())),
	DELIVERY_ADDRESS(FieldNameConstant.DELIVERY_ADDRESS, "Адрес доставки", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateDeliveryAddressVisibility(pair.getLeft(), pair.getRight())),
	LANGUAGE(FieldNameConstant.LANGUAGE, "Язык документа", PortalFieldType.STRING, false, true, null),
	PERIOD_FROM(FieldNameConstant.PERIOD_FROM, "Период с", PortalFieldType.STRING, false, true, null),
	PERIOD_TO(FieldNameConstant.PERIOD_TO, "Период по", PortalFieldType.STRING, false, true, null),
	NAME_OF_CHILD(FieldNameConstant.NAME_OF_CHILD, "ФИО ребёнка", PortalFieldType.STRING, false, true, null),
	DATE_OF_BIRTH_CHILD(FieldNameConstant.DATE_OF_BIRTH_CHILD, "Дата рождения ребёнка", PortalFieldType.DATE, false, true, null),
	PAPER_COPY(FieldNameConstant.PAPER_COPY, "Нужна бумажная версия документа", PortalFieldType.BOOLEAN, false, true, null),
	COMMENT(FieldNameConstant.COMMENT, "Комментарий к заявке", PortalFieldType.STRING, true, true, null),
	DELIVERY_TIME_FROM(FieldNameConstant.DELIVERY_TIME_FROM, "Срок доставки с", PortalFieldType.LOCAL_DATE_TIME, false, true, null),
	DELIVERY_TIME_ON(FieldNameConstant.DELIVERY_TIME_ON, "Срок доставки по", PortalFieldType.LOCAL_DATE_TIME, false, true, null),
	DELIVERY_TYPE(FieldNameConstant.DELIVERY_TYPE, "Тип доставки", PortalFieldType.SELECT, false, true, null),
	PARCEL_TYPE(FieldNameConstant.PARCEL_TYPE, "Вид посылки", PortalFieldType.SELECT, false, true, null),
	ORGANIZATION(FieldNameConstant.ORGANIZATION, "Организация получатель", PortalFieldType.STRING, false, true, null),
	OPENING_HOURS(FieldNameConstant.OPENING_HOURS, "Часы работы организации", PortalFieldType.STRING, false, true, null),
	CONTACT_PERSON(FieldNameConstant.CONTACT_PERSON, "Контактное лицо", PortalFieldType.STRING, false, true, null),
	CONTACT_NUMBER(FieldNameConstant.CONTACT_NUMBER, "Контактный номер телефона", PortalFieldType.STRING, false, true, null),
	PROJECT_CODE(FieldNameConstant.PROJECT_CODE, "Код проекта", PortalFieldType.STRING, false, true, null),
	REQUIRED_SCAN(FieldNameConstant.REQUIRED_SCAN, "Требуется скан подписи", PortalFieldType.CHECKBOX, false, true, null),
	HEAVY_PACKAGE(FieldNameConstant.HEAVY_PACKAGE, "Посылка тяжелая", PortalFieldType.CHECKBOX, false, true, null),
	NEED_POWER_OF_ATTORNEY(FieldNameConstant.NEED_POWER_OF_ATTORNEY, "Нужна доверенность", PortalFieldType.CHECKBOX, false, true, null),
	NEED_PASS(FieldNameConstant.NEED_PASS, "Нужен пропуск", PortalFieldType.CHECKBOX, false, true, null),
	REQUIRED_SIGNING(FieldNameConstant.REQUIRED_SIGNING, "Требуется подписание документов", PortalFieldType.CHECKBOX, false, true, null),
	EXPRESS_DELIVERY(FieldNameConstant.EXPRESS_DELIVERY, "Срочная доставка", PortalFieldType.CHECKBOX, false, true, null),
	NEW_PASSPORT_FILES(FieldNameConstant.NEW_PASSPORT_FILES, "Паспорт",  PortalFieldType.FILE, false, false, null),
	CHILD_BIRTH_CERTIFICATE(FieldNameConstant.CHILD_BIRTH_CERTIFICATE, "Свидетельство о рождении", PortalFieldType.FILE, false, false, null),
	CHANGE_NAME_APPLICATION(FieldNameConstant.CHANGE_NAME_APPLICATION, "Заявление на смену фамилии", PortalFieldType.FILE, false, false, null),
	CHANGE_SNILS(FieldNameConstant.CHANGE_SNILS, "Смена СНИЛС", PortalFieldType.SELECT, false, true, null),
	CHANGE_SNILS_APPLICATION(FieldNameConstant.CHANGE_SNILS_APPLICATION, "Заявление на смену СНИЛС", PortalFieldType.FILE, false, false, null),
	NEW_SNILS(FieldNameConstant.NEW_SNILS, "СНИЛС", PortalFieldType.FILE, false, false, null),
	MARRIAGE_NAME_CHANGE(FieldNameConstant.MARRIAGE_NAME_CHANGE, "Смена фамилии в связи с заключением / расторжением брака", PortalFieldType.CHECKBOX, false, true, null),
	MARRIAGE_CERTIFICATE(FieldNameConstant.MARRIAGE_CERTIFICATE, "Свидетельство о заключении / расторжении брака ", PortalFieldType.FILE, false, false, null),
	ACTUAL_RESIDENCE(FieldNameConstant.ACTUAL_RESIDENCE, "Фактический адрес проживания совпадает с\u00A0местом регистрации", PortalFieldType.BOOLEAN, false, true, null),
	REGION(FieldNameConstant.REGION, "Регион", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateResidenceAddressVisibility((RegistrationChangeRequestData) pair.getLeft(), pair.getRight())),
	DISTRICT(FieldNameConstant.DISTRICT, "Район", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateResidenceAddressVisibility((RegistrationChangeRequestData) pair.getLeft(), pair.getRight())),
	LOCALITY(FieldNameConstant.LOCALITY, "Населенный пункт", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateResidenceAddressVisibility((RegistrationChangeRequestData) pair.getLeft(), pair.getRight())),
	STREET(FieldNameConstant.STREET, "Улица", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateResidenceAddressVisibility((RegistrationChangeRequestData) pair.getLeft(), pair.getRight())),
	HOUSE(FieldNameConstant.HOUSE, "Дом", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateResidenceAddressVisibility((RegistrationChangeRequestData) pair.getLeft(), pair.getRight())),
	FLAT(FieldNameConstant.FLAT, "Квартира", PortalFieldType.STRING, false, true, pair -> PortalFieldVisibilityMapper.INSTANCE.updateResidenceAddressVisibility((RegistrationChangeRequestData) pair.getLeft(), pair.getRight())),
	NEW_PHONE_NUMBER(FieldNameConstant.NEW_PHONE_NUMBER, "Номер телефона", PortalFieldType.STRING, false, true, null),
	SNILS_CHANGE_BY_SELF(FieldNameConstant.SNILS_CHANGE_BY_SELF, "", PortalFieldType.BOOLEAN, false, true, null),
	FULL_USER_NAME(FieldNameConstant.FULL_USER_NAME, "Сотрудник",  PortalFieldType.STRING, false, true, null),
	JUSTIFICATION(FieldNameConstant.JUSTIFICATION, "Обоснование", PortalFieldType.STRING,false, true, null),
	AGREED_BY_THE_HEAD(FieldNameConstant.AGREED_BY_THE_HEAD, "Согласовано руководителем", PortalFieldType.CHECKBOX, false, true, null),
	SUPERVISOR(FieldNameConstant.SUPERVISOR, "Руководитель", PortalFieldType.STRING, false, true, null);

	private final String fieldName;

	private final String title;

	private final PortalFieldType fieldType;

	private final Boolean isUICardAction;

	private final Boolean isUICardBodyVisible;

	@JsonIgnore
	private final Consumer<Pair<RequestData, RequestFormPortalDTO.FieldUIValue>> checkFieldVisibility;

	public static final class FieldNameConstant {
		public static final String TYPE = "type";
		public static final String USER_COMPANY = "userCompany";
		public static final String USER_COMPANY_NAME = "userCompanyName";
		public static final String LAST_NAME_CHANGE_APPLICATION = "lastNameChangeApplication";
		public static final String SNILS_CHANGE_APPLICATION = "snilsChangeApplication";
		public static final String NUMBER_OF_COPIES = "numberOfCopies";
		public static final String SHOW_SALARY = "showSalary";
		public static final String WHERE_REFERENCE = "whereReference";
		public static final String WHERE_REFERENCE_OTHER = "whereReferenceOther";
		public static final String TRAVEL_DATE_FROM = "travelDateFrom";
		public static final String TRAVEL_DATE_TO = "travelDateTo";
		public static final String FULL_NAME_FOREIGN_PASSPORT = "fullNameForeignPassport";
		public static final String DELIVERY_NEED = "deliveryNeed";
		public static final String DELIVERY_ADDRESS = "deliveryAddress";
		public static final String LANGUAGE = "language";
		public static final String PERIOD_FROM = "periodFrom";
		public static final String PERIOD_TO = "periodTo";
		public static final String NAME_OF_CHILD = "nameOfChild";
		public static final String DATE_OF_BIRTH_CHILD = "dateOfBirthChild";
		public static final String PAPER_COPY = "paperCopy";
		public static final String COMMENT = "comment";
		public static final String NEW_PASSPORT_FILES = "newPassportFiles";
		public static final String CHILD_BIRTH_CERTIFICATE = "childBirthCertificate";
		public static final String CHANGE_NAME_APPLICATION = "changeNameApplication";
		public static final String CHANGE_SNILS = "changeSnils";
		public static final String CHANGE_SNILS_APPLICATION = "changeSnilsApplication";
		public static final String NEW_SNILS = "newSnils";
		public static final String MARRIAGE_NAME_CHANGE = "marriageNameChange";
		public static final String MARRIAGE_CERTIFICATE = "marriageCertificate";
		public static final String ACTUAL_RESIDENCE = "actualResidence";
		public static final String REGION = "region";
		public static final String DISTRICT = "district";
		public static final String LOCALITY = "locality";
		public static final String STREET = "street";
		public static final String HOUSE = "house";
		public static final String FLAT = "flat";
		public static final String NEW_PHONE_NUMBER = "newPhoneNumber";
		public static final String SNILS_CHANGE_BY_SELF = "snilsChangeBySelf";
		public static final String FULL_USER_NAME = "fullUserName";
		public static final String JUSTIFICATION = "justification";
		public static final String AGREED_BY_THE_HEAD = "agreedByTheHead";
		public static final String SUPERVISOR = "supervisor";
		public static final String DELIVERY_TIME_FROM = "deliveryTimeFrom";
		public static final String DELIVERY_TIME_ON = "deliveryTimeOn";
		public static final String DELIVERY_TYPE = "deliveryType";
		public static final String PARCEL_TYPE = "parcelType";
		public static final String ORGANIZATION = "organization";
		public static final String OPENING_HOURS = "openingHours";
		public static final String CONTACT_PERSON = "contactPerson";
		public static final String CONTACT_NUMBER = "contactNumber";
		public static final String PROJECT_CODE = "projectCode";
		public static final String REQUIRED_SCAN = "requiredScan";
		public static final String HEAVY_PACKAGE = "heavyPackage";
		public static final String NEED_POWER_OF_ATTORNEY = "needPowerOfAttorney";
		public static final String NEED_PASS = "needPass";
		public static final String REQUIRED_SIGNING = "requiredSigning";
		public static final String EXPRESS_DELIVERY = "expressDelivery";
		public static final String WHERE_REFERENCE_WC = "whereReferenceWC";
		public static final String WHERE_REFERENCE_WB = "whereReferenceWB";
		public static final String WHERE_REFERENCE_EC = "whereReferenceEC";
	}

}
