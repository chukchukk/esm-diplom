package io.demo.dto;

import io.demo.dto.requestdatadtobytype.RequestDataAbstractDTO;
import io.demo.entity.enums.PortalRequestDataCardButton;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestFormPortalDTO {

	private PortalRequestDataCardHeaderDTO requestHeader;

	private RequestDataAbstractDTO requestData;

	private List<Pair<String, FieldUIValue>> requestDataFields;

	private String hint;

	private List<PortalRequestDataCardButton> requestCardButtons;

	private PortalRequestDataCardFilesDTO requestFiles;

	private List<ApprovalHistoryDTO> requestApprovalHistory;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class FieldUIValue {

		private String value;

		private String type;

		private Boolean visible;

		private Boolean isUICardAction;

	}
}
