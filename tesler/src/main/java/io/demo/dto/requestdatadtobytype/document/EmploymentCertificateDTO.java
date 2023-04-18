package io.demo.dto.requestdatadtobytype.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.dto.requestdatadtobytype.category.DocumentCategoryAbstractDTO;
import io.demo.entity.enums.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmploymentCertificateDTO extends DocumentCategoryAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.NUMBER_OF_COPIES)
	private Integer numberOfCopies;

	@JsonProperty(Option.FieldNameConstant.WHERE_REFERENCE)
	private String whereReference;

	@JsonProperty(Option.FieldNameConstant.WHERE_REFERENCE_OTHER)
	private String whereReferenceOther;

	@JsonProperty(Option.FieldNameConstant.TRAVEL_DATE_FROM)
	private LocalDate travelDateFrom;

	@JsonProperty(Option.FieldNameConstant.TRAVEL_DATE_TO)
	private LocalDate travelDateTo;

	@JsonProperty(Option.FieldNameConstant.FULL_NAME_FOREIGN_PASSPORT)
	private String fullNameForeignPassport;

	@JsonProperty(Option.FieldNameConstant.SHOW_SALARY)
	private Boolean showSalary;

	@JsonProperty(Option.FieldNameConstant.LANGUAGE)
	private String language;

}
