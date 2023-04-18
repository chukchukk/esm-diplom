package io.demo.service.tesler.logic;

import io.demo.dto.EmployeeDTO;
import io.demo.service.tesler.meta.EmployeePickListMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.model.core.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EmployeePickListService extends VersionAwareResponseService<EmployeeDTO, User> {

	public EmployeePickListService() {
		super(EmployeeDTO.class, User.class, null, EmployeePickListMeta.class);
	}

	@Override
	protected CreateResult<EmployeeDTO> doCreateEntity(User entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<EmployeeDTO> doUpdateEntity(User entity, EmployeeDTO data, BusinessComponent bc) {
		return null;
	}


}
