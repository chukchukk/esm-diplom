package io.demo.service.tesler.meta;

import io.demo.dto.ApprovalHistoryDTO;
import io.demo.dto.ApprovalHistoryDTO_;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import org.springframework.stereotype.Service;

@Service
public class ApprovalHistoryMeta extends FieldMetaBuilder<ApprovalHistoryDTO> {
	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<ApprovalHistoryDTO> fields, InnerBcDescription bcDescription, Long id, Long parentId) {
		fields.setEnabled(ApprovalHistoryDTO_.createdDate);
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<ApprovalHistoryDTO> fields, InnerBcDescription bcDescription, Long parentId) {
		fields.enableFilter(ApprovalHistoryDTO_.createdDate);
		fields.enableFilter(ApprovalHistoryDTO_.actionType);
		fields.enableFilter(ApprovalHistoryDTO_.actionAuthor);
		fields.enableFilter(ApprovalHistoryDTO_.status);
		fields.enableFilter(ApprovalHistoryDTO_.comment);
	}
}
