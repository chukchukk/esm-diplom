package io.demo.service.tesler;

import io.demo.entity.ApprovalHistory;
import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.ApprovalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalHistoryWriteService {

	private final ApprovalHistoryRepository approvalHistoryRepository;

	public void writeApprovalHistoryWithComment(RequestData requestData, ApprovalHistoryActionType actionType) {
		approvalHistoryRepository.save(
				createBaseApprovalHistory(requestData, actionType)
						.setComment(requestData.getComment())
		);
	}

	public void writeApprovalHistoryWithoutComment(RequestData requestData, ApprovalHistoryActionType actionType) {
		approvalHistoryRepository.save(
				createBaseApprovalHistory(requestData, actionType)
		);
	}

	private ApprovalHistory createBaseApprovalHistory(RequestData requestData, ApprovalHistoryActionType actionType) {
		return new ApprovalHistory()
				.setRequestData(requestData)
				.setSolutionDeadline(requestData.getPlannedDeadline())
				.setStatus(requestData.getStatus())
				.setActionType(actionType);
	}
}
