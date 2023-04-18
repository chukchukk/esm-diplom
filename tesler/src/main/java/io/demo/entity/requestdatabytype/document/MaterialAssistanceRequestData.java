package io.demo.entity.requestdatabytype.document;

import io.demo.entity.enums.RequestType;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.MATERIAL_ASSISTANCE)
@NoArgsConstructor
public class MaterialAssistanceRequestData extends DocumentCategoryRequestData {

	@Column(name = "NAME_OF_CHILD")
	private String nameOfChild;

	@Column(name = "DATE_OF_BIRTH_CHILD")
	private LocalDate dateOfBirthChild;

}
