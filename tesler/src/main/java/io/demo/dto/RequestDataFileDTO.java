package io.demo.dto;

import io.demo.entity.EsmFile;
import io.demo.entity.RequestDataFile;
import io.tesler.api.data.dto.DataResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class RequestDataFileDTO extends DataResponseDTO {

	private String fileId;

	private String fileName;

	private String newFileId;

	private String newFileName;

	private String fileComment;

	private LocalDate creationDate;

	private String createdBy;

	private Boolean addedByEmployee;

	public RequestDataFileDTO(RequestDataFile requestDataFile) {
		this.id = requestDataFile.getId().toString();
		this.vstamp = requestDataFile.getVstamp();
		this.fileId = Optional.ofNullable(requestDataFile.getFile()).map(EsmFile::getId).orElse(null);
		this.fileName = Optional.ofNullable(requestDataFile.getFile()).map(EsmFile::getName).orElse(null);
		this.creationDate = requestDataFile.getCreatedDate().toLocalDate();
		this.createdBy = requestDataFile.getAuthorFullName();
		this.addedByEmployee = requestDataFile.getAddedByEmployee();
	}

}
