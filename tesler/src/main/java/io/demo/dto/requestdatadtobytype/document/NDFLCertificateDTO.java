package io.demo.dto.requestdatadtobytype.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.dto.requestdatadtobytype.category.DocumentCategoryAbstractDTO;
import io.demo.entity.enums.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NDFLCertificateDTO extends DocumentCategoryAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.NUMBER_OF_COPIES)
	private Integer numberOfCopies;

	@JsonProperty(Option.FieldNameConstant.PERIOD_FROM)
	private String periodFrom;

	@JsonProperty(Option.FieldNameConstant.PERIOD_TO)
	private String periodTo;

}
