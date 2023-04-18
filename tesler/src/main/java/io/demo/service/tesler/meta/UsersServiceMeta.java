package io.demo.service.tesler.meta;

import io.demo.dto.UsersDTO;
import io.demo.dto.UsersDTO_;
import io.tesler.core.crudma.bc.impl.InnerBcDescription;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.FieldsMeta;
import io.tesler.core.dto.rowmeta.RowDependentFieldsMeta;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.demo.controller.TeslerRestController.users;

@Service
@RequiredArgsConstructor
public class UsersServiceMeta extends FieldMetaBuilder<UsersDTO> {

	@Override
	public void buildRowDependentMeta(RowDependentFieldsMeta<UsersDTO> fields, InnerBcDescription bcDescription,
										Long id, Long parentId) {
		fields.setDrilldown(
				UsersDTO_.fullName,
				DrillDownType.INNER,
				"/screen/admin/view/adminUserRoleList/" + users + "/" + id
		);

	}

	@Override
	public void buildIndependentMeta(FieldsMeta<UsersDTO> fields, InnerBcDescription bcDescription, Long parentId) {
		fields.enableFilter(UsersDTO_.fullName);

	}

}