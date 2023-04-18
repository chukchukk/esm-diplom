package io.demo.service.tesler.meta;

import io.demo.dto.RequestDataFileDTO;
import io.demo.dto.RequestDataFileDTO_;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestDataFileMeta extends FieldMetaBuilder<RequestDataFileDTO> {
	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<RequestDataFileDTO> fields, InnerBcDescription bcDescription, Long id, Long parentId) {
		fields.setEnabled(
				RequestDataFileDTO_.newFileId,
				RequestDataFileDTO_.newFileName,
				RequestDataFileDTO_.fileComment
		);
		fields.setRequired(
				RequestDataFileDTO_.fileName,
				RequestDataFileDTO_.fileId
		);
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<RequestDataFileDTO> fields, InnerBcDescription bcDescription, Long parentId) {
		fields.setEnabled(
				RequestDataFileDTO_.newFileId,
				RequestDataFileDTO_.newFileName,
				RequestDataFileDTO_.fileComment
		);
		fields.setRequired(
				RequestDataFileDTO_.fileName,
				RequestDataFileDTO_.fileId
		);
	}
}
