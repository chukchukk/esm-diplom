package io.demo.service.camunda.delegate;

import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataRepository;
import io.demo.repository.UserRepository;
import io.demo.service.camunda.utility.CamundaFlowFieldName;
import io.demo.service.email.EmailService;
import io.demo.service.tesler.ApprovalHistoryWriteService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeImplementerDelegate implements JavaDelegate {

	private final RequestDataRepository requestDataRepository;

	private final UserRepository userRepository;

	private final ApprovalHistoryWriteService approvalHistoryWriteService;

	private final EmailService emailService;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Long requestId = (Long) delegateExecution.getVariable(CamundaFlowFieldName.REQUEST_ID);
		Long userId = (Long) delegateExecution.getVariable(CamundaFlowFieldName.IMPLEMENTER_ID);
		Boolean needEmailNotification = (Boolean) delegateExecution.getVariable(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION);
		RequestData requestData = requestDataRepository.findById(requestId).orElseThrow();
		requestData.setImplementer(userRepository.getById(userId));
		RequestData savedData = requestDataRepository.save(requestData);
		approvalHistoryWriteService.writeApprovalHistoryWithoutComment(requestData, ApprovalHistoryActionType.IMPLEMENTER_CHANGE);

//		if (needEmailNotification) {
//			sendNotificationToNewImplementer(requestData, savedData);
//		}
	}

//	private void sendNotificationToNewImplementer(RequestData requestData, RequestData savedData) {
//		List<LOV> implementerRoles = userRoleDivisionRepository.findAllByUserAndActiveIsTrueAndDivision(savedData.getImplementer(), requestData.getDivision())
//				.stream()
//				.map(UserRoleDivision::getInternalRoleCd)
//				.collect(Collectors.toList());
//		if (implementerRoles.containsAll(List.of(Dictionary.UserRoleEnum.IMPLEMENTER.getLov(), Dictionary.UserRoleEnum.HEAD_IMPLEMENTER.getLov())) ||
//				implementerRoles.contains(Dictionary.UserRoleEnum.HEAD_IMPLEMENTER.getLov())) {
//			emailService.assignHeadImplementerNotification(savedData);
//		} else {
//			emailService.assignImplementerNotification(savedData);
//		}
//	}

}
