package io.demo.entity.requestdatabytype.document;

import io.demo.entity.enums.Language;
import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.WhereReference;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.EMPLOYMENT_CERTIFICATE)
@NoArgsConstructor
public class EmploymentCertificateRequestData extends DocumentCategoryRequestData {

	@Column(name = "NUMBER_OF_COPIES")
	private Integer numberOfCopies;

	@Column(name = "WHERE_REFERENCE")
	@Enumerated(EnumType.STRING)
	private WhereReference whereReference;

	@Column(name = "WHERE_REFERENCE_OTHER")
	private String whereReferenceOther;

	@Column(name = "SHOW_SALARY")
	private Boolean showSalary;

	@Column(name = "LANGUAGE")
	@Enumerated(EnumType.STRING)
	private Language language;

	@Column(name = "TRAVEL_DATE_FROM")
	private LocalDate travelDateFrom;

	@Column(name = "TRAVEL_DATE_TO")
	private LocalDate travelDateTo;

	@Column(name = "FULL_NAME_FOREIGN_PASSPORT")
	private String fullNameForeignPassport;

}
