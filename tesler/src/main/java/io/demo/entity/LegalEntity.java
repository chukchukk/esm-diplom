package io.demo.entity;

import io.tesler.model.core.entity.BaseEntity;
import io.demo.entity.enums.LegalEntityStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "LEGAL_ENTITY")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
public class LegalEntity extends BaseEntity implements KeyValueMarker {

	private String fullName;

	@Enumerated(value = EnumType.STRING)
	private LegalEntityStatus status = LegalEntityStatus.Available;

	private Boolean active;

	private String countryName;

	@Override
	public String getKey() {
		return id.toString();
	}

	@Override
	public String getValue() {
		return fullName;
	}
}