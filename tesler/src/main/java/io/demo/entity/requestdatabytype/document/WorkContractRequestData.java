package io.demo.entity.requestdatabytype.document;

import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.WhereReference;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.WORK_CONTRACT)
@NoArgsConstructor
public class WorkContractRequestData extends DocumentCategoryRequestData {

	@Column(name = "NUMBER_OF_COPIES")
	private Integer numberOfCopies;

	@Column(name = "WHERE_REFERENCE")
	@Enumerated(EnumType.STRING)
	private WhereReference whereReference;

	@Column(name = "WHERE_REFERENCE_OTHER")
	private String whereReferenceOther;

}