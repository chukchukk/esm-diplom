package io.demo.service.camunda.delegate;

import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.enums.RequestStatus;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataRepository;
import io.demo.service.camunda.utility.CamundaFlowFieldName;
import io.demo.service.email.EmailService;
import io.demo.service.tesler.ApprovalHistoryWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CancelRequestDelegate implements JavaDelegate {

	private final RequestDataRepository requestDataRepository;

	private final ApprovalHistoryWriteService approvalHistoryWriteService;

	private final EmailService emailService;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		Long requestId = (Long) delegateExecution.getVariable(CamundaFlowFieldName.REQUEST_ID);
		Boolean needEmailNotification = (Boolean) delegateExecution.getVariable(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION);
		RequestData requestData = requestDataRepository.findById(requestId).orElseThrow();
		RequestStatus preUpdateStatus = requestData.getStatus();
		requestData.setStatus(RequestStatus.CANCELLED);
		RequestData savedRequestData = requestDataRepository.save(requestData);
		approvalHistoryWriteService.writeApprovalHistoryWithComment(savedRequestData, ApprovalHistoryActionType.STATUS_CHANGE);
	}
}