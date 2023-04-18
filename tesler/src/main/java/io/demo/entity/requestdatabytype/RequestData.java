package io.demo.entity.requestdatabytype;

import io.demo.entity.LegalEntity;
import io.demo.entity.enums.RequestCategory;
import io.demo.entity.enums.RequestStatus;
import io.demo.entity.enums.RequestType;
import io.tesler.model.core.entity.BaseEntity;
import io.tesler.model.core.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "REQUEST_DATA")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",
		discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("null")
@NoArgsConstructor
public class RequestData extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	private RequestType type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private RequestStatus status;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private LegalEntity legalEntity;

	@ManyToOne
	@JoinColumn(name = "IMPLEMENTER_ID")
	private User implementer;

	@Column(name = "PLANNED_DEADLINE")
	private LocalDate plannedDeadline;

	@Column(name = "ACTUAL_DEADLINE")
	private LocalDate actualDeadline;

	@Column(name = "COMMENT")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "AUTHOR_ID")
	private User author;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY")
	private RequestCategory category;

}
