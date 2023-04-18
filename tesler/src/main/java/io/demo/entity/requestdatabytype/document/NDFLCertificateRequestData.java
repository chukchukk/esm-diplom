package io.demo.entity.requestdatabytype.document;

import io.demo.entity.enums.RequestType;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.NDFL_CERTIFICATE)
@NoArgsConstructor
public class NDFLCertificateRequestData extends DocumentCategoryRequestData {

	@Column(name = "NUMBER_OF_COPIES")
	private Integer numberOfCopies;

	@Column(name = "PERIOD_FROM")
	private String periodFrom;

	@Column(name = "PERIOD_TO")
	private String periodTo;

}
