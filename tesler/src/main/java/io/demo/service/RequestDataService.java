package io.demo.service;

import io.demo.dto.*;
import io.demo.dto.requestdatadtobytype.RequestDataAbstractDTO;
import io.demo.entity.LegalEntity;
import io.demo.entity.RequestDataFile;
import io.demo.entity.enums.ApprovalHistoryActionType;
import io.demo.entity.enums.Option;
import io.demo.entity.enums.PortalRequestDataCardButton;
import io.demo.entity.enums.PortalRequestDataFileButton;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.mapper.ApprovalHistoryMapper;
import io.demo.mapper.RequestDataMapper;
import io.demo.mapper.RequestDataTypeOptionMapper;
import io.demo.repository.*;
import io.demo.service.common.ESMUserRoleService;
import io.demo.service.email.EmailService;
import io.demo.service.tesler.ApprovalHistoryWriteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static io.demo.entity.enums.RequestDataHint.getHintByRequestTypeAndStatus;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestDataService {

	private final RequestTypeOptionRepository requestTypeOptionRepository;

	private final ESMUserRoleService ESMUserRoleService;

	private final ApprovalHistoryRepository approvalHistoryRepository;

	private final LegalEntityRepository legalEntityRepository;

	private final RequestDataRepository requestDataRepository;

	private final UserRepository userRepository;

	private final ApprovalHistoryWriteService approvalHistoryWriteService;

	private final RequestDataFileRepository requestDataFileRepository;

	private final EmailService emailService;

	public Page<RequestDataMainInfoDTO> getCurrentUserRequests(@NonNull PageRequest pageable, boolean isActive) {
		Page<RequestData> requests = requestDataRepository.findAllByUserAndActive(ESMUserRoleService.getCurrentUser(), isActive, pageable);
		List<RequestDataMainInfoDTO> collect = requests
				.stream()
				.map(RequestDataMapper.INSTANCE::toRequestDataMainInfoDTO)
				.collect(Collectors.toList());
		return new PageImpl<>(collect, pageable, requests.getTotalElements());
	}

	public Long createRequest(RequestDataAbstractDTO dto) {
		RequestData requestData = dto
				.getPortalType()
				.getRequestType()
				.getMapRequestDataAbstractDTOToNewRequestData()
				.apply(Pair.of(dto, legalEntityRepository), ESMUserRoleService.getCurrentUser());
		return requestDataRepository.save(requestData).getId();
	}

	public void updateRequest(Long id, RequestDataAbstractDTO dto) {
		RequestData existingRequestData = requestDataRepository.findById(id).orElseThrow();
		existingRequestData
				.getType()
				.getUpdateExistingRequestData()
				.accept(Triple.of(dto, existingRequestData, legalEntityRepository));
		requestDataRepository.save(existingRequestData);

		if (dto.getComment() != null) {
			approvalHistoryWriteService.writeApprovalHistoryWithComment(existingRequestData, ApprovalHistoryActionType.COMMENT_ADD);
		}
	}

	public void addRequestComment(Long id, String comment) {
		RequestData requestData = requestDataRepository.getById(id);
		requestData.setComment(comment);
		approvalHistoryWriteService.writeApprovalHistoryWithComment(requestData, ApprovalHistoryActionType.COMMENT_ADD);
	}

	public RequestFormPortalDTO getRequestDataCard(@NonNull Long id) {
		RequestFormPortalDTO resultDTO = new RequestFormPortalDTO();

		RequestData requestData = requestDataRepository.findByIdAndUser(id, ESMUserRoleService.getCurrentUser())
				.orElseThrow(() -> new AccessDeniedException("Заявка недоступна для просмотра. Авторизуйтесь повторно, " +
						"а если после этого доступ не появится, обратитесь в тех.поддержку"));
		RequestDataAbstractDTO requestDataDTO = requestData
				.getType()
				.getMapRequestDataToDTO()
				.apply(Pair.of(requestData, false));

		String hint = getHintByRequestTypeAndStatus(requestData.getType(), requestData.getStatus())
				.map(requestDataHint -> requestDataHint.getHintText().apply(requestData))
				.orElse(null);

		List<Pair<String, RequestFormPortalDTO.FieldUIValue>> requestDataFields = getRequestDataFields(requestData);

		List<PortalRequestDataCardButton> buttons = PortalRequestDataCardButton.getAvailableButtons(requestData);

		List<RequestDataFile> optionalFiles = requestDataFileRepository.findAllByRequestDataAndActiveIsTrueOrderByCreatedDateDesc(requestData);
		List<FileDTO> employeeFiles = optionalFiles.stream()
				.filter(RequestDataFile::getAddedByEmployee)
				.map(rdf -> new FileDTO().setFileId(rdf.getFile().getId()).setFileName(rdf.getFile().getName()))
				.collect(Collectors.toList());
		List<FileDTO> implementerFiles = optionalFiles.stream()
				.filter(rdf -> !rdf.getAddedByEmployee())
				.map(rdf -> new FileDTO().setFileId(rdf.getFile().getId()).setFileName(rdf.getFile().getName()))
				.collect(Collectors.toList());

		PortalRequestDataCardFilesDTO portalRequestDataCardFilesDTO = new PortalRequestDataCardFilesDTO();
		portalRequestDataCardFilesDTO.setEmployeeFiles(
						new PortalRequestDataCardFilesDTO.PortalFilesAvailableActionsEntryDTO()
								.setFiles(employeeFiles)
								.setActions(PortalRequestDataFileButton.getAvailableEmployeeButtons(requestData))
				)
				.setImplementerFiles(
						new PortalRequestDataCardFilesDTO.PortalFilesAvailableActionsEntryDTO()
								.setFiles(implementerFiles)
								.setActions(PortalRequestDataFileButton.getAvailableImplementerButtons(requestData))
				);
		List<ApprovalHistoryDTO> approvalHistoryList = approvalHistoryRepository.findAllByRequestDataOrderByCreatedDateDesc(requestData).stream()
				.map(o -> ApprovalHistoryMapper.INSTANCE.toApprovalHistoryDTO(o, userRepository))
				.collect(Collectors.toList());

		resultDTO.setRequestHeader(
				new PortalRequestDataCardHeaderDTO()
						.setCreatedDate(requestData.getCreatedDate().toLocalDate())
						.setLegalEntityName(ofNullable(requestData.getLegalEntity()).map(LegalEntity::getFullName).orElse(null))
						.setType(requestData.getType().getValue())
						.setStatus(requestData.getStatus()))
				.setRequestCardButtons(buttons)
				.setRequestFiles(portalRequestDataCardFilesDTO)
				.setRequestApprovalHistory(approvalHistoryList)
				.setRequestData(requestDataDTO)
				.setRequestDataFields(requestDataFields)
				.setHint(hint);
		return resultDTO;
	}

	public RequestDataAbstractDTO getRequestDataEditInfo(@NonNull Long id) {
		RequestData requestData = requestDataRepository.findByIdAndUser(id, ESMUserRoleService.getCurrentUser())
				.orElseThrow(() -> new AccessDeniedException("Заявка недоступна для просмотра. Авторизуйтесь повторно, " +
						"а если после этого доступ не появится, обратитесь в тех.поддержку"));
		RequestDataAbstractDTO requestDataAbstractDTO = requestData
				.getType()
				.getMapRequestDataToDTO()
				.apply(Pair.of(requestData, true));
		return requestDataAbstractDTO
				.setLegalEntityId(ofNullable(requestData.getLegalEntity()).map(l -> l.getId().toString()).orElse(null))
				.setLegalEntityName(ofNullable(requestData.getLegalEntity()).map(LegalEntity::getFullName).orElse(null));
	}

	@NotNull
	private List<Pair<String, RequestFormPortalDTO.FieldUIValue>> getRequestDataFields(RequestData requestData) {
		List<Pair<String, RequestFormPortalDTO.FieldUIValue>> requestDataFields = requestTypeOptionRepository.getAllByRequestType(requestData.getType())
				.stream()
				.map(rtop -> {
							RequestFormPortalDTO.FieldUIValue fieldUIValue = RequestDataTypeOptionMapper.INSTANCE.toFieldUIValue(rtop);
							if (rtop.getOption().getCheckFieldVisibility() != null) {
								rtop.getOption()
										.getCheckFieldVisibility()
										.accept(Pair.of(requestData, fieldUIValue));
							}
							return Pair.of(rtop.getOption().getFieldName(), fieldUIValue);
						}
				)
				.collect(Collectors.toList());
		requestDataFields.addAll(
				List.of(
						Pair.of(Option.FieldNameConstant.TYPE, new RequestFormPortalDTO.FieldUIValue()
								.setType(Option.TYPE.getFieldType().getValue())
								.setValue(Option.TYPE.getTitle())
								.setVisible(Option.TYPE.getIsUICardBodyVisible())
								.setIsUICardAction(Option.TYPE.getIsUICardAction())),
						Pair.of(Option.FieldNameConstant.USER_COMPANY_NAME, new RequestFormPortalDTO.FieldUIValue()
								.setType(Option.COMPANY_NAME.getFieldType().getValue())
								.setValue(Option.COMPANY_NAME.getTitle())
								.setVisible(Option.COMPANY_NAME.getIsUICardBodyVisible())
								.setIsUICardAction(Option.COMPANY_NAME.getIsUICardAction())
						)
				)
		);
		return requestDataFields;
	}

}
