package io.demo.service.tesler.meta;

import io.demo.dto.RequestReadDTO;
import io.demo.dto.RequestReadDTO_;
import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.RequestTypeAvailableReadFields;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestOptionalInfoMeta extends FieldMetaBuilder<RequestReadDTO> {
	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<RequestReadDTO> fields, InnerBcDescription bcDescription, Long id, Long parentId) {
		setHiddenUnusedOptionalFields(fields);
	}

	@Override
	public void buildIndependentMeta(FieldsMeta<RequestReadDTO> fields, InnerBcDescription bcDescription, Long parentId) {

	}

	private void setHiddenUnusedOptionalFields(RowDependentFieldsMeta<RequestReadDTO> fields) {
		RequestType requestType = (RequestType) fields.get(RequestReadDTO_.title).getCurrentValue();

		RequestTypeAvailableReadFields requestTypeAvailableReadFields = RequestTypeAvailableReadFields.getByRequestType(requestType);
		fields.forEach(f -> {
			if (!requestTypeAvailableReadFields.getAvailableOptionalFields().contains(f.getKey()) || f.getCurrentValue() == null) {
				f.setHidden(true);
			}
		});
	}

}
