package io.demo.entity;

import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.enums.RequestStatus;
import io.demo.entity.requestdatabytype.RequestData;
import io.tesler.model.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "APPROVAL_HISTORY")
@Getter
@Setter
@NoArgsConstructor
public class ApprovalHistory extends BaseEntity {

	@Formula(value = "(SELECT u.full_user_name FROM USERS u WHERE u.id = last_upd_by_user_id)")
	private String changedByUserFullName;

	@ManyToOne
	@JoinColumn(name = "REQUEST_DATA_ID")
	private RequestData requestData;

	@Column(name = "SOLUTION_DEADLINE")
	private LocalDate solutionDeadline;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ACTIVE")
	private Boolean active;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "ACTION_TYPE")
	@Enumerated(EnumType.STRING)
	private ApprovalHistoryActionType actionType;

}
