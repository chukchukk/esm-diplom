package io.demo.dto;

import io.demo.entity.enums.PortalRequestDataFileButton;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortalRequestDataCardFilesDTO {

	private PortalFilesAvailableActionsEntryDTO employeeFiles;

	private PortalFilesAvailableActionsEntryDTO implementerFiles;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class PortalFilesAvailableActionsEntryDTO {

		private List<FileDTO> files;

		private List<PortalRequestDataFileButton> actions;

	}

}
