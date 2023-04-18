package io.demo.entity.requestdatabytype.category;

import io.demo.entity.requestdatabytype.RequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class DocumentCategoryRequestData extends RequestData {

	@Column(name = "PAPER_COPY")
	private Boolean paperCopy;

	@Column(name = "DELIVERY_NEED")
	private Boolean deliveryNeed;

	@Column(name = "DELIVERY_ADDRESS")
	private String deliveryAddress;

}