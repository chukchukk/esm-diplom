package io.demo.service.camunda.delegate;

import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.enums.RequestStatus;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataRepository;
import io.demo.repository.UserRepository;
import io.demo.service.camunda.utility.CamundaFlowFieldName;
import io.demo.service.common.ESMUserRoleService;
import io.demo.service.email.EmailService;
import io.demo.service.tesler.ApprovalHistoryWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static io.demo.entity.enums.RequestStatus.IN_PROGRESS;

@Component
@Slf4j
@RequiredArgsConstructor
public class InProgressRequestDelegate implements JavaDelegate {

	private final RequestDataRepository requestDataRepository;

	private final UserRepository userRepository;

	private final ApprovalHistoryWriteService approvalHistoryWriteService;

	private final EmailService emailService;

	private final ESMUserRoleService ESMUserRoleService;

	@Override
	public void execute(DelegateExecution delegateExecution) {
		Long requestId = (Long) delegateExecution.getVariable(CamundaFlowFieldName.REQUEST_ID);
		Long userId = (Long) delegateExecution.getVariable(CamundaFlowFieldName.IMPLEMENTER_ID);
		Boolean needEmailNotification = (Boolean) delegateExecution.getVariable(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION);
		RequestData requestData = requestDataRepository.findById(requestId).orElseThrow();
		RequestStatus currentStatus = requestData.getStatus();
		requestData.setStatus(IN_PROGRESS)
				.setImplementer(userRepository.getById(userId));

		RequestData savedData = requestDataRepository.save(requestData);
		approvalHistoryWriteService.writeApprovalHistoryWithoutComment(savedData, ApprovalHistoryActionType.STATUS_CHANGE);
	}

}
