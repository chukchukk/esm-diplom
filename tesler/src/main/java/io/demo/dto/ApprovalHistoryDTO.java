package io.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.demo.entity.ApprovalHistory;
import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.enums.RequestStatus;
import io.demo.mapper.ApprovalHistoryMapper;
import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.core.util.filter.provider.impl.DateValueProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalHistoryDTO extends DataResponseDTO {

	private ApprovalHistoryActionType actionType;

	private String actionAuthor;

	private Long actionAuthorId;

	@JsonIgnore
	private Long lastUpdBy;

	private RequestStatus status;

	@SearchParameter(name = "comment")
	private String comment;

	@SearchParameter(name = "createdDate", provider = DateValueProvider.class)
	private LocalDateTime createdDate;

	private String color;

	public ApprovalHistoryDTO(ApprovalHistory entity) {
		ApprovalHistoryMapper.INSTANCE.fromApprovalHistoryEntity(entity, this);
	}

}
