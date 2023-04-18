package io.demo.service.common;

import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.PreAction;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;

import static io.demo.controller.TeslerRestController.*;

@UtilityClass
public class PreActions {

	private static final Map<String, String> MAP_TO_ADD_COMMENT_POPUP = Map.of(
			request.getName(), addCommentRequest.getName(),
			requestList.getName(), addCommentRequestList.getName(),
			requestNew.getName(), addCommentRequestListNew.getName(),
			requestInProgress.getName(), addCommentRequestListInProgress.getName()
	);

	private static final Map<String, String> MAP_TO_CANCEL_POPUP = Map.of(
			request.getName(), addCancelCommentForm.getName(),
			requestList.getName(), addCancelCommentList.getName(),
			requestNew.getName(), addCancelCommentListNew.getName(),
			requestInProgress.getName(), addCancelCommentListInProgress.getName()
	);

	private static final Map<String, String> MAP_TO_COMPLETE_REQUEST_POPUP = Map.of(
			request.getName(), completeRequest.getName(),
			requestList.getName(), completeRequestList.getName(),
			requestInProgress.getName(), completeRequestListInProgress.getName()
	);

	private static final Map<String, String> MAP_TO_COMPLETE_REQUEST_WITH_FILE_POPUP = Map.of(
			request.getName(), completeWithFileRequest.getName(),
			requestList.getName(), completeWithFileRequestList.getName(),
			requestInProgress.getName(), completeWithFileRequestListInProgress.getName()
	);

	private static final Map<String, String> MAP_TO_REJECT_REQUEST_POPUP = Map.of(
			request.getName(), rejectRequest.getName(),
			requestList.getName(), rejectRequestList.getName(),
			requestNew.getName(), rejectRequestListNew.getName(),
			requestInProgress.getName(), rejectRequestListInProgress.getName()
	);

	private static final Map<String, String> MAP_TO_NEED_EXTRA_INFO_POPUP = Map.of(
			request.getName(), needExtraInfoRequest.getName(),
			requestList.getName(), needExtraInfoRequestList.getName(),
			requestInProgress.getName(), needExtraInfoRequestListInProgress.getName()
	);

	private static final Map<String, String> MAP_TO_SOLUTION_DEADLINE_CHANGE_POPUP = Map.of(
			request.getName(), solutionDeadlineChangeRequest.getName(),
			requestList.getName(), solutionDeadlineChangeRequestList.getName(),
			requestNew.getName(), solutionDeadlineChangeRequestListNew.getName(),
			requestInProgress.getName(), solutionDeadlineChangeRequestListInProgress.getName()
	);

	private static Map<String, String> getFormPopupParams(String popupBcName) {
		return Map.of(
				"type", "bc",
				"buttonOkText", "ОК",
				"bc", Objects.requireNonNull(popupBcName)
		);
	}

	public static PreAction showAddCommentPopup(BusinessComponent bc) {
		if (MAP_TO_ADD_COMMENT_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_ADD_COMMENT_POPUP.get(bc.getName())));
		}
		return null;
	}

	public static PreAction showCancelPopup(BusinessComponent bc) {
		if (MAP_TO_CANCEL_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_CANCEL_POPUP.get(bc.getName())));
		}
		return null;
	}

	public static PreAction showCompleteRequestPopup(BusinessComponent bc) {
		if (MAP_TO_COMPLETE_REQUEST_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_COMPLETE_REQUEST_POPUP.get(bc.getName())));
		}
		return null;
	}

	public static PreAction showCompleteRequestWithFilePopup(BusinessComponent bc) {
		if (MAP_TO_COMPLETE_REQUEST_WITH_FILE_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_COMPLETE_REQUEST_WITH_FILE_POPUP.get(bc.getName())));
		}
		return null;
	}

	public static PreAction showNeedExtraInfoPopup(BusinessComponent bc) {
		if (MAP_TO_NEED_EXTRA_INFO_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_NEED_EXTRA_INFO_POPUP.get(bc.getName())));
		}
		return null;
	}

	public static PreAction showRejectRequestPopup(BusinessComponent bc) {
		if (MAP_TO_REJECT_REQUEST_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_REJECT_REQUEST_POPUP.get(bc.getName())));
		}
		return null;
	}

	public static PreAction showSolutionDeadlineChangePopup(BusinessComponent bc) {
		if (MAP_TO_SOLUTION_DEADLINE_CHANGE_POPUP.containsKey(bc.getName())) {
			return PreAction.custom("", getFormPopupParams(MAP_TO_SOLUTION_DEADLINE_CHANGE_POPUP.get(bc.getName())));
		}
		return null;
	}

}
