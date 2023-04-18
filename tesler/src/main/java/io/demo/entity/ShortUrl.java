package io.demo.entity;

import io.demo.entity.enums.ShortUrlType;
import io.tesler.model.core.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "SHORT_URL", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"TYPE", "ENTITY_ID"})
})
@NoArgsConstructor
public class ShortUrl extends BaseEntity {

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "TYPE")
	@Enumerated(value = EnumType.STRING)
	private ShortUrlType type;

	@Column(name = "ENTITY_ID")
	private Long entityId;

}
