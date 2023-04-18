package io.demo.dto.requestdatadtobytype;

import com.fasterxml.jackson.annotation.*;
import io.demo.dto.requestdatadtobytype.document.*;
import io.demo.dto.requestdatadtobytype.personaldatachange.*;
import io.demo.entity.enums.Option;
import io.demo.entity.enums.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		property = "type",
		visible = true
)
@JsonSubTypes(value = {
		@JsonSubTypes.Type(value = EmploymentCertificateDTO.class, name = RequestType.RequestTypeKeyConstant.EMPLOYMENT_CERTIFICATE),
		@JsonSubTypes.Type(value = NDFLCertificateDTO.class, name = RequestType.RequestTypeKeyConstant.NDFL_CERTIFICATE),
		@JsonSubTypes.Type(value = WorkBookDTO.class, name = RequestType.RequestTypeKeyConstant.WORK_BOOK),
		@JsonSubTypes.Type(value = WorkContractDTO.class, name = RequestType.RequestTypeKeyConstant.WORK_CONTRACT),
		@JsonSubTypes.Type(value = LumpSumBirthDTO.class, name = RequestType.RequestTypeKeyConstant.LUMP_SUM_BIRTH),
		@JsonSubTypes.Type(value = LumpSumChildcareDTO.class, name = RequestType.RequestTypeKeyConstant.LUMP_SUM_CHILDCARE),
		@JsonSubTypes.Type(value = MaterialAssistanceDTO.class, name = RequestType.RequestTypeKeyConstant.MATERIAL_ASSISTANCE),
		@JsonSubTypes.Type(value = PassportChangeDTO.class, name = RequestType.RequestTypeKeyConstant.PASSPORT_CHANGE),
		@JsonSubTypes.Type(value = DocumentsChangeDTO.class, name = RequestType.RequestTypeKeyConstant.DOCUMENTS_CHANGE),
		@JsonSubTypes.Type(value = RegistrationChangeDTO.class, name = RequestType.RequestTypeKeyConstant.REGISTRATION_CHANGE),
		@JsonSubTypes.Type(value = ChildBirthDTO.class, name = RequestType.RequestTypeKeyConstant.CHILD_BIRTH),
		@JsonSubTypes.Type(value = PhoneNumberChangeDTO.class, name = RequestType.RequestTypeKeyConstant.PHONE_NUMBER_CHANGE)
})
@NoArgsConstructor
public abstract class RequestDataAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.TYPE)
	private RequestPortalType portalType;

	@JsonProperty(Option.FieldNameConstant.COMMENT)
	private String comment;

	@JsonProperty(Option.FieldNameConstant.USER_COMPANY)
	private String legalEntityId;

	@JsonProperty(Option.FieldNameConstant.USER_COMPANY_NAME)
	private String legalEntityName;

	@Getter
	@SuppressWarnings("java:S115")
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum RequestPortalType {

		EMPLOYMENT_CERTIFICATE(RequestType.EMPLOYMENT_CERTIFICATE),
		NDFL_CERTIFICATE(RequestType.NDFL_CERTIFICATE),
		WORK_BOOK(RequestType.WORK_BOOK),
		WORK_CONTRACT(RequestType.WORK_CONTRACT),
		LUMP_SUM_BIRTH(RequestType.LUMP_SUM_BIRTH),
		LUMP_SUM_CHILDCARE(RequestType.LUMP_SUM_CHILDCARE),
		MATERIAL_ASSISTANCE(RequestType.MATERIAL_ASSISTANCE),
		PASSPORT_CHANGE(RequestType.PASSPORT_CHANGE),
		DOCUMENTS_CHANGE(RequestType.DOCUMENTS_CHANGE),
		REGISTRATION_CHANGE(RequestType.REGISTRATION_CHANGE),
		PHONE_NUMBER_CHANGE(RequestType.PHONE_NUMBER_CHANGE),
		CHILD_BIRTH(RequestType.CHILD_BIRTH);

		RequestPortalType(RequestType requestType) {
			this.requestType = requestType;
			this.key = requestType.getKey();
			this.value = requestType.getValue();
			this.category = requestType.getCategory().name();
			this.isInternal = requestType.getIsInternal();
		}

		@JsonIgnore
		private final RequestType requestType;

		private final String key;

		private final String value;

		private final String category;

		private final Boolean isInternal;

		public static RequestPortalType getByRequestType(RequestType requestType) {
			return Arrays.stream(RequestPortalType.values())
					.filter(rpt -> rpt.getRequestType().equals(requestType))
					.findFirst()
					.orElseThrow();
		}

	}

}
