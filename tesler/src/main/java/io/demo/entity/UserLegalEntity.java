package io.demo.entity;

import io.demo.entity.enums.EmploymentStatus;
import io.tesler.model.core.entity.BaseEntity;
import io.tesler.model.core.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER_LEGAL_ENTITY")
@NoArgsConstructor
public class UserLegalEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "LEGAL_ENTITY_ID")
	private LegalEntity legalEntity;

	@Column(name = "EMPLOYMENT_STATUS")
	@Enumerated(EnumType.STRING)
	private EmploymentStatus employmentStatus;

	@Column(name = "IS_DEFAULT")
	private Boolean isDefault;

}
