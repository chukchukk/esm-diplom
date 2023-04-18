package io.demo.service.tesler.meta;

import io.demo.dto.EmployeeDTO;
import io.demo.dto.EmployeeDTO_;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class EmployeePickListMeta extends FieldMetaBuilder<EmployeeDTO> {

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<EmployeeDTO> fields, InnerBcDescription bcDescription,
										Long id, Long parentId) {
		fields.setEnabled(EmployeeDTO_.employeeName, EmployeeDTO_.id);
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<EmployeeDTO> fields, InnerBcDescription bcDescription,
										Long parentId) {
		fields.enableFilter(EmployeeDTO_.employeeName);
	}

}