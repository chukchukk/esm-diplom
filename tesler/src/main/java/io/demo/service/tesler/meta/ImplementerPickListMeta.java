package io.demo.service.tesler.meta;

import io.demo.dto.ImplementerDTO;
import io.demo.dto.ImplementerDTO_;
import io.tesler.api.data.dto.DataResponseDTO_;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class ImplementerPickListMeta extends FieldMetaBuilder<ImplementerDTO> {

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<ImplementerDTO> fields, InnerBcDescription bcDescription, Long id, Long parentId) {
		fields.setEnabled(
				DataResponseDTO_.id,
				ImplementerDTO_.implementerName
		);
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<ImplementerDTO> fields, InnerBcDescription bcDescription, Long parentId) {
	}
}