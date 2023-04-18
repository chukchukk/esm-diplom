package io.demo.entity;


import io.demo.entity.requestdatabytype.RequestData;
import io.tesler.model.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "REQUEST_DATA_FILE")
@Getter
@Setter
@NoArgsConstructor
public class RequestDataFile extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "REQUEST_DATA_ID")
	private RequestData requestData;

	@Embedded
	private EsmFile file;

	@Formula("(select u.full_user_name from users u where u.id = created_by_user_id)")
	private String authorFullName;

	private Boolean addedByEmployee;

	private Boolean active;

}
