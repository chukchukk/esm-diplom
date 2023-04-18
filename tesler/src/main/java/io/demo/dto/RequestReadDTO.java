package io.demo.dto;

import io.demo.entity.LegalEntity;
import io.demo.entity.enums.*;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.service.tesler.util.LocalDateValueProvider;
import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.core.util.filter.provider.impl.DateTimeValueProvider;
import io.tesler.core.util.filter.provider.impl.EnumValueProvider;
import io.tesler.core.util.filter.provider.impl.LongValueProvider;
import io.tesler.model.core.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@NoArgsConstructor
public class RequestReadDTO extends DataResponseDTO {

	@SearchParameter(name = "type", provider = EnumValueProvider.class)
	private RequestType title;

	@SearchParameter(name = "category", provider = EnumValueProvider.class)
	private RequestCategory category;

	@SearchParameter(name = "createdDate", provider = DateTimeValueProvider.class)
	private LocalDateTime createdDate;

	@SearchParameter(name = "author.fullUserName")
	private String author;

	@SearchParameter(name = "user.fullUserName")
	private String employee;

	@SearchParameter(name = "implementer.fullUserName")
	private String implementer;

	@SearchParameter(name = "implementer.id", provider = LongValueProvider.class)
	private Long implementerId;

	@SearchParameter(name = "plannedDeadline", provider = LocalDateValueProvider.class)
	private LocalDate plannedDeadline;

	@SearchParameter(name = "actualDeadline", provider = LocalDateValueProvider.class)
	private LocalDate actualDeadline;

	@SearchParameter(name = "legalEntity.fullName")
	private String legalEntityName;

	@SearchParameter(name = "status", provider = EnumValueProvider.class)
	private RequestStatus status;

	private String newRequestViewerName;

	private Long newRequestViewerId;

	private LocalDate newActualDeadline;

	private String newActualDeadlineComment;

	private String rejectComment;

	private String completeComment;

	private String requestComment;

	private String extraInfoComment;

	private String cancelComment;

	private String color;

	private String deadlineColor;

	/**
	 * Опциональные поля для разных типов заявок
	 */

	private Integer numberOfCopies;

	private String showSalary;

	private String whereReference;

	private String whereReferenceOther;

	private LocalDate travelDateFrom;

	private LocalDate travelDateTo;

	private String fullNameForeignPassport;

	private String paperCopy;

	private String deliveryNeed;

	private String deliveryAddress;

	private String language;

	private String periodFrom;

	private String periodTo;

	private String nameOfChild;

	private LocalDate dateOfBirthChild;

	//PersonalDataChange

	private String changeSnils;

	private String marriageNameChange;

	private String actualResidence;

	private String region;

	private String district;

	private String locality;

	private String street;

	private String house;

	private String flat;

	private String newPhoneNumber;

	//NeedInfoPopup

	@SearchParameter
	private Boolean snilsChangeBySelf;

	private String needInfoFileId;

	private String needInfoFileName;

	private String fileId;

	private String fileName;

	private String newFileId;

	private String newFileName;

	private String newFileComment;

	public RequestReadDTO(RequestData request) {
		this.id = request.getId().toString();
		this.vstamp = request.getVstamp();
		this.title = request.getType();
		this.category = ofNullable(request.getType()).map(RequestType::getCategory).orElse(null);
		this.createdDate =  request.getCreatedDate();
		this.legalEntityName = ofNullable(request.getLegalEntity()).map(LegalEntity::getFullName).orElse("Не требуется");
		this.employee = ofNullable(request.getUser()).map(User::getFullName).orElse(null);
		this.author = ofNullable(request.getAuthor()).map(User::getFullName).orElse(null);
		this.implementer = ofNullable(request.getImplementer()).map(User::getFullName).orElse("Не назначен");
		this.implementerId = ofNullable(request.getImplementer()).map(User::getId).orElse(null);
		this.plannedDeadline = request.getPlannedDeadline();
		this.actualDeadline = request.getActualDeadline();
		this.status = request.getStatus();
		this.color = ofNullable(request.getStatus()).map(stat -> stat.getColor().getValue()).orElse(null);
		this.deadlineColor = request.getActualDeadline().isBefore(LocalDate.now()) ? BgColor.PALE_RED.getValue() : null;

		if (request.getType() != null && request.getStatus() != null) {
			request.getType()
					.getFillRequestReadDTOFields()
					.accept(Pair.of(this, request));
		}
	}

}