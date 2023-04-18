package io.demo.service.tesler.meta;

import io.demo.dto.RequestWriteFileDTO;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class RequestWriteFileMeta extends FieldMetaBuilder<RequestWriteFileDTO> {
	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<RequestWriteFileDTO> fields, InnerBcDescription bcDescription, Long id, Long parentId) {

	}

	@Override
	public void buildIndependentMeta(FieldsMeta<RequestWriteFileDTO> fields, InnerBcDescription bcDescription, Long parentId) {

	}
}
