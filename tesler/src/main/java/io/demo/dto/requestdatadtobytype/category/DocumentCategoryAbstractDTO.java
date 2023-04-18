package io.demo.dto.requestdatadtobytype.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.dto.requestdatadtobytype.RequestDataAbstractDTO;
import io.demo.entity.enums.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class DocumentCategoryAbstractDTO extends RequestDataAbstractDTO {

	@JsonProperty(Option.FieldNameConstant.PAPER_COPY)
	private Boolean paperCopy;

	@JsonProperty(Option.FieldNameConstant.DELIVERY_NEED)
	private Boolean deliveryNeed;

	@JsonProperty(Option.FieldNameConstant.DELIVERY_ADDRESS)
	private String deliveryAddress;

}
