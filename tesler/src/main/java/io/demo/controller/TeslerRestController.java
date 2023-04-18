package io.demo.controller;

import io.demo.service.tesler.RequestDataFileService;
import io.demo.service.tesler.logic.*;
import io.tesler.core.crudma.bc.BcIdentifier;
import io.tesler.core.crudma.bc.EnumBcIdentifier;
import io.tesler.core.crudma.bc.impl.AbstractEnumBcSupplier;
import io.tesler.core.crudma.bc.impl.BcDescription;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This is actually an analog of a usual @RestController.
 * When you add enum, you just add rest endpoints, that tesler UI can call.
 * We could actually make a usual @RestController and make it Generic,
 * but current enum approach shows, that it is less error-prone in huge enterprise projects
 * (because single line in this enum creates >5 rest endpoints)
 */
@Getter
public enum TeslerRestController implements EnumBcIdentifier {

	// @formatter:on
	request(RequestReadService.class),
		addCancelCommentForm(request, RequestReadService.class),
		addCancelCommentList(request, RequestReadService.class),
		addCancelCommentListInProgress(request, RequestReadService.class),
		addCancelCommentListNew(request, RequestReadService.class),
	addFileToRequest(RequestReadService.class),
	addImplementerToRequest(RequestReadService.class),
		addCommentRequest(request, RequestReadService.class),
		completeRequest(request, RequestReadService.class),
		completeWithFileRequest(request, RequestReadService.class),
		needExtraInfoRequest(request, RequestReadService.class),
		rejectRequest(request, RequestReadService.class),
		solutionDeadlineChangeRequest(request, RequestReadService.class),
	requestList(RequestReadService.class),
		addCommentRequestList(requestList, RequestReadService.class),
		completeRequestList(requestList, RequestReadService.class),
		completeWithFileRequestList(requestList, RequestReadService.class),
		needExtraInfoRequestList(requestList, RequestReadService.class),
		rejectRequestList(requestList, RequestReadService.class),
		solutionDeadlineChangeRequestList(requestList, RequestReadService.class),
	requestInProgress(RequestReadService.class),
		addCommentRequestListInProgress(requestInProgress, RequestReadService.class),
		completeRequestListInProgress(requestInProgress, RequestReadService.class),
		completeWithFileRequestListInProgress(requestInProgress, RequestReadService.class),
		needExtraInfoRequestListInProgress(requestInProgress, RequestReadService.class),
		rejectRequestListInProgress(requestInProgress, RequestReadService.class),
		solutionDeadlineChangeRequestListInProgress(requestInProgress, RequestReadService.class),
	requestNew(RequestReadService.class),
		addCommentRequestListNew(requestNew, RequestReadService.class),
		rejectRequestListNew(requestNew, RequestReadService.class),
		solutionDeadlineChangeRequestListNew(requestNew, RequestReadService.class),
	requestArchive(RequestReadService.class),
		requestFiles(request, RequestDataFileService.class),
	requestListImplementerExpiringDeadline(RequestReadService.class),
	requestListHeadImplementerExpiringDeadline(RequestReadService.class),
	requestListObserved(RequestReadService.class),
	requestOptional(request, RequestOptionalInfoService.class),
		implementerPickListPopupRequest(request, ImplementerPickListService.class),
		implementerPickListPopupRequestForm(addImplementerToRequest, ImplementerPickListService.class),
		implementerPickListPopupRequestList(requestList, ImplementerPickListService.class),
		implementerPickListPopupRequestListNew(requestNew, ImplementerPickListService.class),
		implementerPickListPopupRequestListInProgress(requestInProgress, ImplementerPickListService.class),
		requestApprovalHistory(request, ApprovalHistoryService.class),
	users(UsersService.class);

	// @formatter:on

	public static final EnumBcIdentifier.Holder<TeslerRestController> Holder = new Holder<>(TeslerRestController.class);

	private final BcDescription bcDescription;

	TeslerRestController(String parentName, Class<?> serviceClass, boolean refresh) {
		this.bcDescription = buildDescription(parentName, serviceClass, refresh);
	}

	TeslerRestController(String parentName, Class<?> serviceClass) {
		this(parentName, serviceClass, false);
	}

	TeslerRestController(BcIdentifier parent, Class<?> serviceClass, boolean refresh) {
		this(parent == null ? null : parent.getName(), serviceClass, refresh);
	}

	TeslerRestController(BcIdentifier parent, Class<?> serviceClass) {
		this(parent, serviceClass, false);
	}

	TeslerRestController(Class<?> serviceClass, boolean refresh) {
		this((String) null, serviceClass, refresh);
	}

	TeslerRestController(Class<?> serviceClass) {
		this((String) null, serviceClass, false);
	}

	@Component
	public static class BcSupplier extends AbstractEnumBcSupplier<TeslerRestController> {

		public BcSupplier() {
			super(TeslerRestController.Holder);
		}

	}

	public static TeslerRestController getBcByName(String bcName) {
		return Arrays.stream(TeslerRestController.values())
				.filter(bc -> bc.getName().equals(bcName))
				.findFirst()
				.orElseThrow();
	}
}
