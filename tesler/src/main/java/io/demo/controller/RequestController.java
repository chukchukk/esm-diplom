package io.demo.controller;

import io.demo.dto.*;
import io.demo.dto.portal.requestcreate.DictionaryKeyValueDTO;
import io.demo.dto.requestdatadtobytype.RequestDataAbstractDTO;
import io.demo.entity.enums.RequestCategory;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataRepository;
import io.demo.service.RequestDataService;
import io.demo.service.camunda.EsmCamundaProvider;
import io.demo.service.camunda.utility.CamundaFlowFieldName;
import io.demo.service.common.ESMUserRoleService;
import io.demo.service.portal.PortalFileService;
import io.demo.service.portal.builder.RequestDataMainDictionaryBuilder;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static io.demo.entity.enums.camunda.RequestAction.*;
import static io.demo.service.camunda.utility.CamundaProcessDefinitionKey.DOCUMENT_REQUEST;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestDataService requestDataService;

	private final EsmCamundaProvider esmCamundaProvider;

	private final RequestDataRepository requestDataRepository;

	private final PortalFileService portalFileService;

	private final RequestDataMainDictionaryBuilder requestDataMainDictionaryBuilder;

	@GetMapping("/dictionaries/{category_key}")
	public ResponseEntity<Map<String, List<DictionaryKeyValueDTO>>> getDictionaries(@PathVariable(name = "category_key") RequestCategory categoryKey) {
		return ResponseEntity.ok(
				requestDataMainDictionaryBuilder.getInstance(categoryKey).buildFieldNameToDictionariesMap()
		);
	}

	@GetMapping("/listByCurrentUser")
	public ResponseEntity<Page<RequestDataMainInfoDTO>> getUserRequests(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
																		@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
																		@RequestParam(value = "isActive", required = false, defaultValue = "true") boolean isActive) {
		return ResponseEntity.ok(requestDataService.getCurrentUserRequests(PageRequest.of(pageNumber, pageSize, Sort.sort(RequestData.class).by(RequestData::getCreatedDate).descending()), isActive));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateRequest(@PathVariable(name = "id") Long id,
												@RequestBody RequestDataAbstractDTO dto) {
		requestDataService.updateRequest(id, dto);
		return ResponseEntity.ok(null);
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<RequestDataAbstractDTO> getRequestDataEditInfo(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(requestDataService.getRequestDataEditInfo(id));
	}

	@GetMapping("/card/{id}")
	public ResponseEntity<RequestFormPortalDTO> getRequestDataCard(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(requestDataService.getRequestDataCard(id));
	}

	@PostMapping("/comment/{id}")
	public ResponseEntity<Void> addRequestComment(@PathVariable(name = "id") Long id, @RequestParam(name = "comment") String comment) {
		requestDataService.addRequestComment(id, comment);
		return ResponseEntity.ok(null);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<Long> createRequest(@RequestBody RequestDataAbstractDTO dto) {
		Long requestId = requestDataService.createRequest(dto);
		esmCamundaProvider.startProcessInstance(DOCUMENT_REQUEST,
				DOCUMENT_REQUEST + requestId,
				Variables.putValue(CamundaFlowFieldName.REQUEST_ID, requestId)
		);
		return ResponseEntity.ok(requestId);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/uploadFiles/{requestId}")
	public ResponseEntity<Void> uploadFilesToRequest(@PathVariable(name = "requestId") Long requestId, @RequestPart(name = "files", required = false) List<MultipartFile> files) {
		if (files != null) {
			portalFileService.uploadRequestDataFiles(files, requestId);
		}
		return ResponseEntity.ok(null);
	}

	@PostMapping("/cancel/{id}")
	public ResponseEntity<Void> cancelRequest(@PathVariable(name = "id") Long id) {
		esmCamundaProvider.completeUserTaskWithVariablesInReturn(
				DOCUMENT_REQUEST + id,
				Variables
						.putValue(CamundaFlowFieldName.ACTION, CANCEL)
						.putValue(CamundaFlowFieldName.REQUEST_ID, id)
						.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, true)
		);
		return ResponseEntity.ok(null);
	}

	@PostMapping("/reject/{id}")
	public ResponseEntity<Void> rejectRequest(@PathVariable(name = "id") Long id) {
		esmCamundaProvider.completeUserTaskWithVariablesInReturn(
				DOCUMENT_REQUEST + id,
				Variables.putValue(CamundaFlowFieldName.ACTION, REJECT)
		);
		return ResponseEntity.ok(null);
	}

	@PostMapping("/sendToWork/{id}")
	public ResponseEntity<Void> sendToWork(@PathVariable(name = "id") Long id) {
		esmCamundaProvider.completeUserTaskWithVariablesInReturn(
				DOCUMENT_REQUEST + id,
				Variables.putValue(CamundaFlowFieldName.ACTION, SEND_TO_WORK)
						.putValue(CamundaFlowFieldName.IMPLEMENTER_ID, requestDataRepository.getById(id).getImplementer().getId())
						.putValue(CamundaFlowFieldName.NEED_EMAIL_NOTIFICATION, true)
		);
		return ResponseEntity.ok(null);
	}

}
