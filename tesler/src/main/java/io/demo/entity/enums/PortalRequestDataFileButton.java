package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import io.demo.entity.requestdatabytype.RequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.demo.entity.enums.RequestStatus.*;

@Getter
@AllArgsConstructor
public enum PortalRequestDataFileButton {

	ADD_FILES_BUTTON("addFilesButton", List.of(NEW, NEED_INFO), true, false),
	DOWNLOAD_ZIP("downloadZip", List.of(IN_PROGRESS, COMPLETE, REJECTED, CANCELLED), true, true);

	@JsonValue
	private final String value;

	private final List<RequestStatus> availableInRequestStatus;

	private final Boolean isEmployeeFiles;

	private final Boolean isImplementerFiles;

	public static List<PortalRequestDataFileButton> getAvailableEmployeeButtons(@NonNull RequestData requestData) {
		return Arrays.stream(PortalRequestDataFileButton.values()).filter(
				button -> button.getIsEmployeeFiles() && button.getAvailableInRequestStatus().contains(requestData.getStatus())
		).collect(Collectors.toList());
	}

	public static List<PortalRequestDataFileButton> getAvailableImplementerButtons(@NonNull RequestData requestData) {
		return Arrays.stream(PortalRequestDataFileButton.values()).filter(
				button -> button.getIsImplementerFiles() && button.getAvailableInRequestStatus().contains(requestData.getStatus())
		).collect(Collectors.toList());
	}

}
