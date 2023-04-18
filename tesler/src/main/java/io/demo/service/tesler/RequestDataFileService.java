package io.demo.service.tesler;

import io.demo.conf.tesler.file.TeslerFileServiceMinio;
import io.demo.controller.TeslerRestController;
import io.demo.dto.RequestDataFileDTO;
import io.demo.dto.RequestDataFileDTO_;
import io.demo.entity.RequestDataFile;
import io.demo.entity.RequestDataFile_;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataFileRepository;
import io.demo.repository.RequestDataRepository;
import io.demo.service.common.ESMUserRoleService;
import io.demo.service.tesler.meta.RequestDataFileMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.service.action.ActionScope;
import io.tesler.core.service.action.Actions;
import io.tesler.core.service.action.ActionsBuilder;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.demo.conf.tesler.dictionary.Dictionary.UserRoleEnum.HEAD_IMPLEMENTER;
import static io.demo.conf.tesler.dictionary.Dictionary.UserRoleEnum.IMPLEMENTER;
import static io.demo.controller.TeslerRestController.request;
import static io.demo.conf.tesler.dictionary.Dictionary.UserRoleEnum.*;
import static io.demo.controller.TeslerRestController.requestFiles;

@Service
public class RequestDataFileService extends VersionAwareResponseService<RequestDataFileDTO, RequestDataFile> {

	private final RequestDataRepository requestDataRepository;

	private final ESMUserRoleService userRoleService;

	private final RequestDataFileRepository requestDataFileRepository;

	private final TeslerFileServiceMinio fileService;

	public RequestDataFileService(RequestDataRepository requestDataRepository, ESMUserRoleService userRoleService,
									RequestDataFileRepository requestDataFileRepository, TeslerFileServiceMinio fileService) {
		super(RequestDataFileDTO.class, RequestDataFile.class, null, RequestDataFileMeta.class);
		this.requestDataRepository = requestDataRepository;
		this.userRoleService = userRoleService;
		this.requestDataFileRepository = requestDataFileRepository;
		this.fileService = fileService;
	}

	@Override
	protected CreateResult<RequestDataFileDTO> doCreateEntity(RequestDataFile entity, BusinessComponent bc) {
		requestDataFileRepository.save(entity);
		return new CreateResult<>(entityToDto(bc, entity));
	}

	@Override
	protected ActionResultDTO<RequestDataFileDTO> doUpdateEntity(RequestDataFile entity, RequestDataFileDTO data, BusinessComponent bc) {
		if (data.isFieldChanged(RequestDataFileDTO_.newFileId)) {
			RequestDataFile file = new RequestDataFile()
					.setRequestData(requestDataRepository.getById(bc.getParentIdAsLong()))
					.setFile(fileService.moveToDefaultBucket(data.getNewFileId()).orElse(null))
					.setAddedByEmployee(false)
					.setActive(true);
			requestDataFileRepository.save(file);
		}
		return new ActionResultDTO<>(entityToDto(bc, entity));
	}

	@Override
	protected Specification<RequestDataFile> getSpecification(BusinessComponent bc) {
		if (requestFiles.isBc(bc)) {
			RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
			return super.getSpecification(bc).and(
					(root, cq, cb) ->
							cb.and(
									cb.equal(root.get(RequestDataFile_.REQUEST_DATA), Hibernate.unproxy(requestData)),
									cb.isTrue(root.get(RequestDataFile_.ACTIVE))
							)
			);
		}
		return super.getSpecification(bc);
	}

	@Override
	public Actions<RequestDataFileDTO> getActions() {
		return Actions.<RequestDataFileDTO>builder()
				.addGroup(
						"actions",
						"Действия",
						0,
						addActionsGroup(Actions.builder()).build()
				)
				.save().withoutIcon().add()

				.newAction()
				.action("saveUpdates", "OK")
				.withAutoSaveBefore()
				.scope(ActionScope.BC)
				.invoker((bc, data) -> {
					RequestDataFile file = new RequestDataFile()
							.setRequestData(requestDataRepository.getById(bc.getParentIdAsLong()))
							.setFile(fileService.moveToDefaultBucket(data.getNewFileId()).orElse(null))
							.setAddedByEmployee(false)
							.setActive(true);
					requestDataFileRepository.save(file);
					return new ActionResultDTO<RequestDataFileDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestList"
						));
					}
				)
				.add()
				.newAction()
				.action("cancelUpdates", "Отменить")
				.scope(ActionScope.BC)
				.withoutAutoSaveBefore()
				.invoker((bc, dto) -> new ActionResultDTO<RequestDataFileDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestList"
						))
				)
				.add()

				.newAction()
				.action("backToRequest", "К заявке")
				.scope(ActionScope.BC)
				.withoutAutoSaveBefore()
				.invoker((bc, dto) -> new ActionResultDTO<RequestDataFileDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/requestInfo/" + request + "/" + bc.getParentId()
						))
				)
				.add()

				.build();
	}

	private ActionsBuilder<RequestDataFileDTO> addActionsGroup(ActionsBuilder<RequestDataFileDTO> builder) {
		return builder
				.newAction()
				.action("addFile", "Добавить")
				.scope(ActionScope.BC)
				.withAutoSaveBefore()
				.invoker((bc, data) -> new ActionResultDTO<RequestDataFileDTO>().setAction(
						PostAction.drillDown(
								DrillDownType.INNER,
								"/screen/requests/view/addFilesToRequest/" + TeslerRestController.addFileToRequest + "/" + bc.getParentId()
						)
				))
				.available(bc -> userRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER, IMPLEMENTER)))
				.add();
	}
}
