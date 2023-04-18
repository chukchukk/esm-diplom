package io.demo.service.tesler.logic;

import io.demo.conf.tesler.dictionary.Dictionary;
import io.demo.dto.UsersDTO;
import io.demo.service.tesler.meta.UsersServiceMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;

import io.tesler.model.core.entity.User;
import io.tesler.model.core.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UsersService extends VersionAwareResponseService<UsersDTO, User> {

	public UsersService() {
		super(UsersDTO.class, User.class, null, UsersServiceMeta.class);
	}

	@Override
	protected CreateResult<UsersDTO> doCreateEntity(User entity, BusinessComponent bc) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected UsersDTO entityToDto(BusinessComponent bc, User entity) {
		UsersDTO dto = super.entityToDto(bc, entity);
		dto.setReadRoles(entity.getUserRoleList().stream()
				.filter(UserRole::getActive)
				.map(UserRole::getInternalRoleCd)
				.map(internalRoleCd -> Dictionary.UserRoleEnum.valueOf(internalRoleCd.getKey()))
				.map(Dictionary.UserRoleEnum::getValue)
				.collect(Collectors.joining(", ")));
		return dto;
	}

	@Override
	protected ActionResultDTO<UsersDTO> doUpdateEntity(User entity, UsersDTO data, BusinessComponent bc) {
		return null;
	}

}
