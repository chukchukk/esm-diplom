package io.demo.service.tesler.logic;

import io.demo.conf.tesler.file.TeslerFileServiceMinio;
import io.demo.controller.TeslerRestController;
import io.demo.dto.RequestReadDTO;
import io.demo.dto.RequestReadDTO_;
import io.demo.entity.RequestDataFile;
import io.demo.entity.UserLegalEntity;
import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.RequestData_;
import io.demo.entity.requestdatabytype.personaldatachange.DocumentsChangeRequestData;
import io.demo.repository.RequestDataFileRepository;
import io.demo.repository.RequestDataRepository;
import io.demo.repository.UserLegalEntityRepository;
import io.demo.service.camunda.EsmCamundaProvider;
import io.demo.service.camunda.utility.CamundaFlowFieldName;
import io.demo.service.camunda.utility.CamundaProcessDefinitionKey;
import io.demo.service.common.ESMUserRoleService;
import io.demo.service.common.PostActions;
import io.demo.service.common.PreActions;
import io.demo.service.common.ZipService;
import io.demo.service.email.EmailService;
import io.demo.service.tesler.ApprovalHistoryWriteService;
import io.demo.service.tesler.meta.RequestReadMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.MessageType;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.dto.rowmeta.PreAction;
import io.tesler.core.exception.BusinessException;
import io.tesler.core.service.action.ActionScope;
import io.tesler.core.service.action.Actions;
import io.tesler.core.service.action.ActionsBuilder;
import io.tesler.model.core.entity.User;
import org.apache.commons.lang3.tuple.Pair;
import org.camunda.bpm.engine.variable.Variables;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.demo.conf.tesler.dictionary.Dictionary.UserRoleEnum.*;
import static io.demo.controller.TeslerRestController.*;
import static io.demo.entity.enums.RequestStatus.*;
import static io.demo.entity.enums.camunda.RequestAction.*;
import static io.demo.service.camunda.utility.CamundaProcessDefinitionKey.DOCUMENT_REQUEST;

@Service
public class RequestReadService extends VersionAwareResponseService<RequestReadDTO, RequestData> {

	private final ESMUserRoleService userRoleService;

	private final RequestDataRepository requestDataRepository;

	private final EsmCamundaProvider esmCamundaProvider;

	private final ApprovalHistoryWriteService approvalHistoryWriteService;

	private final TeslerFileServiceMinio fileService;

	private final RequestDataFileRepository requestDataFileRepository;

	private final EmailService emailService;

	private final ZipService zipService;

	private final UserLegalEntityRepository userLegalEntityRepository;

	public RequestReadService(ESMUserRoleService userRoleService, RequestDataRepository requestDataRepository,
								EsmCamundaProvider esmCamundaProvider, ApprovalHistoryWriteService approvalHistoryWriteService,
								TeslerFileServiceMinio fileService, RequestDataFileRepository requestDataFileRepository,
								EmailService emailService, ZipService zipService, UserLegalEntityRepository userLegalEntityRepository) {
		super(RequestReadDTO.class, RequestData.class, null, RequestReadMeta.class);
		this.userRoleService = userRoleService;
		this.requestDataRepository = requestDataRepository;
		this.esmCamundaProvider = esmCamundaProvider;
		this.approvalHistoryWriteService = approvalHistoryWriteService;
		this.fileService = fileService;
		this.requestDataFileRepository = requestDataFileRepository;
		this.emailService = emailService;
		this.zipService = zipService;
		this.userLegalEntityRepository = userLegalEntityRepository;
	}

	@Override
	protected Specification<RequestData> getSecuritySpecification(InnerBcDescription bc) {
		User currentUser = userRoleService.getCurrentUser();
		Optional<UserLegalEntity> userLegalEntity = userLegalEntityRepository.findByUserAndIsDefaultIsTrue(currentUser);
		Specification<RequestData> requestDivisionSpecification = super.getSecuritySpecification(bc).and(
				(root, cq, cb) -> cb.or(
						cb.equal(root.get(RequestData_.legalEntity), userLegalEntity.map(UserLegalEntity::getLegalEntity).orElse(null)),
						cb.isNull(root.get(RequestData_.legalEntity))
				)
		);

		if (requestList.isBc(bc)) {
			return requestDivisionSpecification.and(
					(root, cq, cb) -> root.get(RequestData_.status).in(NEW, IN_PROGRESS, NEED_INFO)
			);
		}
		if (requestNew.isBc(bc)) {
			return requestDivisionSpecification.and(
					(root, cq, cb) -> cb.equal(root.get(RequestData_.status), NEW)
			);
		}
		if (requestInProgress.isBc(bc)) {
			return requestDivisionSpecification.and(
					(root, cq, cb) -> root.get(RequestData_.status).in(IN_PROGRESS, NEED_INFO)
			);
		}
		if (requestArchive.isBc(bc)) {
			return requestDivisionSpecification.and(
					(root, cq, cb) -> root.get(RequestData_.status).in(COMPLETE, CANCELLED, REJECTED)
			);
		}
		if (requestListImplementerExpiringDeadline.isBc(bc)) {
			return super.getSecuritySpecification(bc).and(
					(root, cq, cb) -> cb.and(
							cb.equal(root.get(RequestData_.IMPLEMENTER), currentUser),
							root.get(RequestData_.STATUS).in(IN_PROGRESS, NEED_INFO),
							cb.lessThanOrEqualTo(root.get(RequestData_.actualDeadline), LocalDate.now())
					)
			);
		}
		if (requestListHeadImplementerExpiringDeadline.isBc(bc)) {
			return requestDivisionSpecification.and(
					(root, cq, cb) -> cb.and(
							root.get(RequestData_.STATUS).in(List.of(NEW, IN_PROGRESS, NEED_INFO)),
							cb.lessThanOrEqualTo(root.get(RequestData_.actualDeadline), LocalDate.now())
					)
			);
		}

		return super.getSecuritySpecification(bc);
	}

	@Override
	protected CreateResult<RequestReadDTO> doCreateEntity(RequestData requestData, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<RequestReadDTO> doUpdateEntity(RequestData entity, RequestReadDTO data, BusinessComponent bc) {
		if (data.isFieldChanged(RequestReadDTO_.implementerId) &&
				data.getImplementer() != null) {
			if (entity.getImplementer() == null) {
				esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getId(),
						Variables.putValue(CamundaFlowFieldName.ACTION, TAKE_TO_WORK)
								.putValue(CamundaFlowFieldName.IMPLEMENTER_ID, data.getImplementerId())
								.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, true)
				);
			} else {
				esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getId(),
						Variables.putValue(CamundaFlowFieldName.ACTION, CHANGE_IMPLEMENTER)
								.putValue(CamundaFlowFieldName.IMPLEMENTER_ID, data.getImplementerId())
								.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, true)
				);
			}
		}
		if (data.isFieldChanged(RequestReadDTO_.cancelComment)) {
			RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
			requestData.setComment(data.getCancelComment());
			requestDataRepository.save(entity);
			esmCamundaProvider.completeUserTaskWithVariablesInReturn(
					DOCUMENT_REQUEST + bc.getParentIdAsLong(),
					Variables
							.putValue(CamundaFlowFieldName.ACTION, CANCEL)
							.putValue(CamundaFlowFieldName.REQUEST_ID, bc.getParentIdAsLong())
							.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, false)

			);
		}

		if (data.isFieldChanged(RequestReadDTO_.newActualDeadline) && data.getNewActualDeadline() != null) {
			if (data.getNewActualDeadline().isAfter(LocalDate.now())) {
				entity.setActualDeadline(data.getNewActualDeadline())
						.setComment(data.getNewActualDeadlineComment());
				approvalHistoryWriteService.writeApprovalHistoryWithComment(entity, ApprovalHistoryActionType.SOLUTION_DEADLINE_CHANGE);
				requestDataRepository.save(entity);
			} else {
				throw new BusinessException().addPostAction(PostAction.showMessage(
								MessageType.ERROR,
								"Выберите дату не ранее сегодняшнего числа"))
						.addPopup("Выберите дату не ранее сегодняшнего числа");
			}

		}
		if (data.isFieldChanged(RequestReadDTO_.rejectComment)) {
			esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getParentIdAsLong(),
					Variables.putValue(CamundaFlowFieldName.ACTION, REJECT).putValue(CamundaFlowFieldName.REJECT_COMMENT, data.getRejectComment()));
		}

		if (data.isFieldChanged(RequestReadDTO_.fileId)) {
			RequestDataFile file = new RequestDataFile()
					.setRequestData(requestDataRepository.getById(bc.getParentIdAsLong()))
					.setFile(fileService.moveToDefaultBucket(data.getFileId()).orElse(null))
					.setAddedByEmployee(false)
					.setActive(true);
			requestDataFileRepository.save(file);
			approvalHistoryWriteService.writeApprovalHistoryWithoutComment(requestDataRepository.getById(bc.getParentIdAsLong()), ApprovalHistoryActionType.FILES_ADD);
		}
		if (data.isFieldChanged(RequestReadDTO_.completeComment)) {
			esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getParentIdAsLong(),
					Variables.putValue(CamundaFlowFieldName.ACTION, EXECUTE).putValue(CamundaFlowFieldName.COMPLETE_COMMENT, data.getCompleteComment()));
		}

		if (data.isFieldChanged(RequestReadDTO_.newFileId)) {
			RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
			RequestDataFile file = new RequestDataFile()
					.setRequestData(requestData)
					.setFile(fileService.moveToDefaultBucket(data.getNewFileId()).orElse(null))
					.setAddedByEmployee(false)
					.setActive(true);
			requestDataFileRepository.save(file);
			approvalHistoryWriteService.writeApprovalHistoryWithoutComment(requestData, ApprovalHistoryActionType.FILES_ADD);

			ByteArrayOutputStream byteArrayOutputStream = zipService.generateZipWithRequestDataFiles(requestData, false);
			emailService.addFilesToRequestByImplementerEmployeeNotification(requestData, byteArrayOutputStream);
		}
		if (data.isFieldChanged(RequestReadDTO_.newFileComment)) {
			RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
			requestData.setComment(data.getNewFileComment());
			approvalHistoryWriteService.writeApprovalHistoryWithComment(requestData, ApprovalHistoryActionType.COMMENT_ADD);
		}

		if (data.isFieldChanged(RequestReadDTO_.requestComment)) {
			RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
			requestData.setComment(data.getRequestComment());
			approvalHistoryWriteService.writeApprovalHistoryWithComment(requestData, ApprovalHistoryActionType.COMMENT_ADD);
			emailService.commentAddNotification(requestData, data.getRequestComment());
		}
		if (data.isFieldChanged(RequestReadDTO_.extraInfoComment) && data.isFieldChanged(RequestReadDTO_.needInfoFileId)) {
			esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getParentIdAsLong(),
					Variables.putValue(CamundaFlowFieldName.ACTION, CALL_FOR_EXTRA_INFO).putValue(CamundaFlowFieldName.EXTRA_INFO_COMMENT, data.getExtraInfoComment()));
			RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
			RequestDataFile file = new RequestDataFile()
					.setRequestData(requestData)
					.setFile(fileService.moveToDefaultBucket(data.getNeedInfoFileId()).orElse(null))
					.setAddedByEmployee(false)
					.setActive(true);
			requestDataFileRepository.save(file);
			emailService.needInfoNotification(requestData);
		}
		if (data.isFieldChanged(RequestReadDTO_.extraInfoComment) && !data.isFieldChanged(RequestReadDTO_.needInfoFileId)) {
			esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getParentIdAsLong(),
					Variables.putValue(CamundaFlowFieldName.ACTION, CALL_FOR_EXTRA_INFO).putValue(CamundaFlowFieldName.EXTRA_INFO_COMMENT, data.getExtraInfoComment()));
			RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
			emailService.needInfoNotification(requestData);
		}
		if (data.isFieldChanged(RequestReadDTO_.snilsChangeBySelf)) {
			RequestData unproxyEntity = Hibernate.unproxy(entity, RequestData.class);
			((DocumentsChangeRequestData) unproxyEntity).setSnilsChangeBySelf(data.getSnilsChangeBySelf());
		}
		return new ActionResultDTO<>(entityToDto(bc, entity));
	}

	@Override
	protected RequestReadDTO entityToDto(BusinessComponent bc, RequestData entity) {
		RequestData unproxyEntity = Hibernate.unproxy(entity, RequestData.class);
		RequestReadDTO dto = new RequestReadDTO(unproxyEntity);
		if (userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER))
				&& dto.getImplementerId() == null) {
			dto.setImplementer("Добавить");
		}
		return dto;
	}


	@Override
	public Actions<RequestReadDTO> getActions() {
		return Actions.<RequestReadDTO>builder()

				.addGroup(
						"actions",
						"Действия",
						0,
						addActionsGroup(Actions.builder()).build()
				)

				.save().withoutIcon().add()

				.newAction()
				.action("saveUpdates", "OK")
				.scope(ActionScope.BC)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestList"
						))
				)
				.add()
				.newAction()
				.action("cancelUpdates", "Отменить")
				.scope(ActionScope.BC)
				.withoutAutoSaveBefore()
				.invoker((bc, dto) -> new ActionResultDTO<RequestReadDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestList"
						))
				)
				.add()

				.newAction()
				.action("saveViewer", "OK")
				.scope(ActionScope.BC)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestViewerList/" + request + "/" + bc.getId()
						))
				)
				.add()


				.newAction()
				.action("openFilesView", "Вкладка файлы")
				.scope(ActionScope.BC)
				.withoutAutoSaveBefore()
				.invoker((bc, dto) -> new ActionResultDTO<RequestReadDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestFiles/" + request + "/" + bc.getId()
						))
				)
				.add()

				.newAction()
				.action("saveFile", "OK")
				.scope(ActionScope.BC)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestFiles/" + request + "/" + bc.getId()
						))
				)
				.add()

				.newAction()
				.action("cancelAddingFile", "Отменить")
				.scope(ActionScope.BC)
				.withoutAutoSaveBefore()
				.invoker((bc, dto) -> new ActionResultDTO<RequestReadDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestFiles/" + request + "/" + bc.getId()
						))
				)
				.add()
				.build();
	}

	private ActionsBuilder<RequestReadDTO> addActionsGroup(ActionsBuilder<RequestReadDTO> builder) {
		return builder
				.newAction()
				.action("assignToAnother", "Назначить другому")
				.scope(ActionScope.RECORD)
				.withoutAutoSaveBefore()
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostActions.showImplementerPickList(bc)))
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return userRoleService.currentUserRoleIsEqualsTo(List.of(IMPLEMENTER))
							&& List.of(IN_PROGRESS, NEED_INFO).contains(requestData.getStatus())
							&& requestData.getImplementer().equals(userRoleService.getCurrentUser());
				})
				.add()

				.action("appointImplementer", "Назначить исполнителя")
				.scope(ActionScope.RECORD)
				.withoutAutoSaveBefore()
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostActions.showImplementerPickList(bc)))
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER))
							&& Objects.equals(NEW, requestData.getStatus());
				})
				.add()

				.newAction()
				.action("assignCurrentUser", "Взять в работу")
				.scope(ActionScope.RECORD)
				.withoutAutoSaveBefore()
				.withPreAction(PreAction.confirm("После подтверждения ты будешь указан в качестве исполнителя. Проложить?"))
				.invoker((bc, data) -> {
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					if (requestData.getImplementer() == null) {
						esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getId(),
								Variables.putValue(CamundaFlowFieldName.ACTION, TAKE_TO_WORK)
										.putValue(CamundaFlowFieldName.IMPLEMENTER_ID, userRoleService.getCurrentUser().getId())
										.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, false)
						);
					} else {
						esmCamundaProvider.completeUserTaskWithVariablesInReturn(CamundaProcessDefinitionKey.DOCUMENT_REQUEST + bc.getId(),
								Variables.putValue(CamundaFlowFieldName.ACTION, CHANGE_IMPLEMENTER)
										.putValue(CamundaFlowFieldName.IMPLEMENTER_ID, userRoleService.getCurrentUser().getId())
										.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, false)
						);
					}
					return new ActionResultDTO<>();
				})
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return (userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER,
							IMPLEMENTER)) && NEW.equals(requestData.getStatus()));
				})
				.add()

				.newAction()
				.action("changeSolutionDeadLine", "Изменить срок исполнения")
				.scope(ActionScope.RECORD)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostAction.refreshBc(TeslerRestController.getBcByName(bc.getParentName()))))
				.withPreAction(PreActions::showSolutionDeadlineChangePopup)
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return (NEW.equals(requestData.getStatus()) || (IN_PROGRESS.equals(requestData.getStatus()) &&
							(userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER)) || (userRoleService.currentUserRoleIsEqualsTo(List.of(IMPLEMENTER))
									&& userRoleService.getCurrentUser().equals(requestData.getImplementer())))));
				})
				.add()

				.newAction()
				.action("rejectRequest", "Отклонить")
				.scope(ActionScope.RECORD)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostAction.refreshBc(TeslerRestController.getBcByName(bc.getParentName()))))
				.withPreAction(PreActions::showRejectRequestPopup)
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return NEW.equals(requestData.getStatus()) ||
							(IN_PROGRESS.equals(requestData.getStatus()) &&
							(userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER)) ||
									(userRoleService.currentUserRoleIsEqualsTo(List.of(IMPLEMENTER)) &&
											userRoleService.getCurrentUser().equals(requestData.getImplementer()))));
				})
				.add()

				.newAction()
				.action("completeRequest", "Выполнена")
				.scope(ActionScope.RECORD)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostAction.refreshBc(TeslerRestController.getBcByName(bc.getParentName()))))
				.withPreAction(this::completeRequestDataPreAction)
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return IN_PROGRESS.equals(requestData.getStatus()) && (userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER))
							|| (userRoleService.currentUserRoleIsEqualsTo(List.of(IMPLEMENTER)) && userRoleService.getCurrentUser().equals(requestData.getImplementer())));
				})
				.add()

				.newAction()
				.action("addComment", "Добавить комментарий")
				.scope(ActionScope.RECORD)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostAction.refreshBc(TeslerRestController.getBcByName(bc.getParentName()))))
				.withPreAction(PreActions::showAddCommentPopup)
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					if (requestData.getStatus() == null) {
						return false;
					}
					return (NEW.equals(requestData.getStatus()) && !userRoleService.currentUserRoleIsEqualsTo(List.of(VIEWER))) || (List.of(IN_PROGRESS, NEED_INFO).contains(requestData.getStatus()) &&
							(userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER)) || (userRoleService.currentUserRoleIsEqualsTo(List.of(IMPLEMENTER))
									&& userRoleService.getCurrentUser().equals(requestData.getImplementer())))
					);
				})
				.add()

				.newAction()
				.action("needExtraInfo", "Дозапрос информации")
				.scope(ActionScope.RECORD)
				.invoker((bc, data) -> new ActionResultDTO<RequestReadDTO>().setAction(PostAction.refreshBc(TeslerRestController.getBcByName(bc.getParentName()))))
				.withPreAction(PreActions::showNeedExtraInfoPopup)
				.available(bc -> {
					if (bc.getId() == null) {
						return false;
					}
					RequestData requestData = requestDataRepository.getById(bc.getIdAsLong());
					return IN_PROGRESS.equals(requestData.getStatus()) && (userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER))
							|| (userRoleService.currentUserRoleIsEqualsTo(List.of(IMPLEMENTER)) && userRoleService.getCurrentUser().equals(requestData.getImplementer())));
				})
				.add();
	}

	private PreAction completeRequestDataPreAction(BusinessComponent bc) {
		if (List.of(requestList.getName(), requestInProgress.getName(), request.getName()).contains(bc.getName()) && bc.getId() != null) {
			return requestDataRepository.getById(bc.getIdAsLong())
					.getType()
					.getCategory()
					.getCompletePreActionProvider()
					.apply(Pair.of(requestDataRepository.getById(bc.getIdAsLong()), bc));
		}
		return null;
	}

}
