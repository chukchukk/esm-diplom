package io.demo.dto;

import io.demo.entity.RequestDataFile;
import io.tesler.api.data.dto.DataResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestWriteFileDTO extends DataResponseDTO {

	private List<String> bulkIds;

	public RequestWriteFileDTO(RequestDataFile requestDataFile) {
		this.id = requestDataFile.getId().toString();
		this.vstamp = requestDataFile.getVstamp();
	}

}
