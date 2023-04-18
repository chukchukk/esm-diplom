package io.demo.service.tesler.logic;

import io.demo.dto.RequestReadDTO;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.RequestData_;
import io.demo.service.tesler.meta.RequestOptionalInfoMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RequestOptionalInfoService extends VersionAwareResponseService<RequestReadDTO, RequestData> {
	public RequestOptionalInfoService() {
		super(RequestReadDTO.class, RequestData.class, null, RequestOptionalInfoMeta.class);
	}

	@Override
	protected CreateResult<RequestReadDTO> doCreateEntity(RequestData entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<RequestReadDTO> doUpdateEntity(RequestData entity, RequestReadDTO data, BusinessComponent bc) {
		return null;
	}

	@Override
	protected Specification<RequestData> getSpecification(BusinessComponent bc) {
		return super.getSpecification(bc).and((root, cq, cb) -> cb.equal(root.get(RequestData_.ID), bc.getParentIdAsLong()));
	}
}
