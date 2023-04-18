package io.demo.service.tesler.logic;

import io.demo.dto.ImplementerDTO;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataRepository;
import io.demo.service.common.ESMUserRoleService;
import io.demo.service.tesler.meta.ImplementerPickListMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.model.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImplementerPickListService extends VersionAwareResponseService<ImplementerDTO, User> {

	private final ESMUserRoleService ESMUserRoleService;

	private final RequestDataRepository requestDataRepository;

	public ImplementerPickListService(ESMUserRoleService ESMUserRoleService, RequestDataRepository requestDataRepository) {
		super(ImplementerDTO.class, User.class, null, ImplementerPickListMeta.class);
		this.ESMUserRoleService = ESMUserRoleService;
		this.requestDataRepository = requestDataRepository;
	}

	@Override
	protected CreateResult<ImplementerDTO> doCreateEntity(User entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<ImplementerDTO> doUpdateEntity(User entity, ImplementerDTO data, BusinessComponent bc) {
		return null;
	}

	@Override
	protected Specification<User> getSpecification(BusinessComponent bc) {
		RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
		User currentUser = ESMUserRoleService.getCurrentUser();
		Specification<User> generalSpecification = super.getSpecification(bc);
		if (requestData.getImplementer() == null) {
			return generalSpecification.and((root, cq, cb) ->
					cb.notEqual(root, currentUser)
			);
		}
		return generalSpecification.and((root, cq, cb) ->
				cb.notEqual(root, requestData.getImplementer()));
	}
}
