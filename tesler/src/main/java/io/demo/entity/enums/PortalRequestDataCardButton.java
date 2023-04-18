package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.demo.entity.requestdatabytype.RequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.demo.entity.enums.RequestStatus.*;
import static io.demo.entity.enums.RequestType.*;

@Getter
@AllArgsConstructor
public enum PortalRequestDataCardButton {

	CANCEL_BUTTON("cancelButton", List.of(NEW, IN_PROGRESS, NEED_INFO), List.of()),
	SEND_TO_WORK_BUTTON("sendToWorkButton", List.of(NEED_INFO), List.of()),
	EDIT_BUTTON("editButton", List.of(NEW, NEED_INFO), List.of(RequestType.PASSPORT_CHANGE, RequestType.CHILD_BIRTH));

	@JsonValue
	private final String value;

	@JsonIgnore
	private final List<RequestStatus> availableInRequestStatus;

	@JsonIgnore
	private final List<RequestType> notAvailableRequestTypes;

	public static List<PortalRequestDataCardButton> getAvailableButtons(@NonNull RequestData requestData) {
		List<PortalRequestDataCardButton> portalRequestDataCardButtons =  Arrays.stream(PortalRequestDataCardButton.values())
				.filter(button -> button.getAvailableInRequestStatus().contains(requestData.getStatus()) &&
						!button.getNotAvailableRequestTypes().contains(requestData.getType()))
				.collect(Collectors.toList());
		return portalRequestDataCardButtons;
	}

}
