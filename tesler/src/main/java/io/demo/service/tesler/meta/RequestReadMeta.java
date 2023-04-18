package io.demo.service.tesler.meta;

import io.demo.controller.TeslerRestController;
import io.demo.dto.RequestReadDTO;
import io.demo.dto.RequestReadDTO_;
import io.demo.entity.enums.RequestStatus;
import io.demo.entity.enums.RequestType;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.personaldatachange.DocumentsChangeRequestData;
import io.demo.repository.RequestDataRepository;
import io.demo.service.common.ESMUserRoleService;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.demo.conf.tesler.dictionary.Dictionary.UserRoleEnum.HEAD_IMPLEMENTER;
import static io.demo.controller.TeslerRestController.*;
import static io.demo.entity.enums.RequestType.DOCUMENTS_CHANGE;
import static io.demo.entity.enums.SnilsChange.INDEPENDENTLY;

@Service
@RequiredArgsConstructor
public class RequestReadMeta extends FieldMetaBuilder<RequestReadDTO> {

	private final RequestDataRepository requestDataRepository;

	private final ESMUserRoleService ESMUserRoleService;

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<RequestReadDTO> fields, InnerBcDescription bcDescription,
			Long id, Long parentId) {
		RequestData requestData = requestDataRepository.getById(id);
		fields.setDrilldown(
				RequestReadDTO_.title,
				DrillDownType.INNER,
				"/screen/requests/view/requestInfo/" + TeslerRestController.request + "/" + id
		);
		fields.setEnabled(
				RequestReadDTO_.implementerId,
				RequestReadDTO_.implementer,
				RequestReadDTO_.fileId,
				RequestReadDTO_.fileName,
				RequestReadDTO_.newActualDeadline,
				RequestReadDTO_.newActualDeadlineComment,
				RequestReadDTO_.rejectComment,
				RequestReadDTO_.completeComment,
				RequestReadDTO_.requestComment,
				RequestReadDTO_.extraInfoComment,
				RequestReadDTO_.newFileId,
				RequestReadDTO_.newFileName,
				RequestReadDTO_.newFileComment,
				RequestReadDTO_.needInfoFileId,
				RequestReadDTO_.needInfoFileName,
				RequestReadDTO_.snilsChangeBySelf,
				RequestReadDTO_.newRequestViewerId,
				RequestReadDTO_.newRequestViewerName,
				RequestReadDTO_.cancelComment
		);
		if (ESMUserRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER))) {
			fields.setDrilldown(
					RequestReadDTO_.implementer,
					DrillDownType.INNER,
					"/screen/requests/view/addImplementer/" + TeslerRestController.addImplementerToRequest + "/" + id
			);
		}
		if (!ESMUserRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER)) || requestArchive.isBc(bcDescription)) {
			fields.setDisabled(RequestReadDTO_.implementer);
		}
		fields.setRequired(
				RequestReadDTO_.newActualDeadline,
				RequestReadDTO_.newActualDeadlineComment,
				RequestReadDTO_.completeComment,
				RequestReadDTO_.requestComment,
				RequestReadDTO_.rejectComment,
				RequestReadDTO_.extraInfoComment,
				RequestReadDTO_.fileName,
				RequestReadDTO_.newFileId,
				RequestReadDTO_.newFileName,
				RequestReadDTO_.cancelComment
		);
		if (List.of(needExtraInfoRequest.getName(), needExtraInfoRequestList.getName(), needExtraInfoRequestListInProgress.getName()).contains(bcDescription.getName())) {
			if (!(DOCUMENTS_CHANGE.equals(requestData.getType()) &&
					INDEPENDENTLY.equals(((DocumentsChangeRequestData)requestData).getChangeSnils()))) {
				fields.get(RequestReadDTO_.snilsChangeBySelf).setHidden(true);
			}
		}
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<RequestReadDTO> fields, InnerBcDescription bcDescription,
			Long parentId) {
		fields.enableFilter(RequestReadDTO_.title);
		fields.enableFilter(RequestReadDTO_.createdDate);
		fields.enableFilter(RequestReadDTO_.legalEntityName);
		fields.enableFilter(RequestReadDTO_.author);
		fields.enableFilter(RequestReadDTO_.implementer);
		fields.enableFilter(RequestReadDTO_.status);
		fields.setEnumFilterValues(fields, RequestReadDTO_.status, RequestStatus.values());
		fields.setEnumFilterValues(fields, RequestReadDTO_.title, RequestType.values());
		fields.enableFilter(RequestReadDTO_.actualDeadline);
		fields.enableFilter(RequestReadDTO_.plannedDeadline);
		if (!ESMUserRoleService.currentUserRoleIsEqualsTo(List.of(HEAD_IMPLEMENTER))) {
			fields.setHidden(RequestReadDTO_.plannedDeadline);
		}
	}

}
