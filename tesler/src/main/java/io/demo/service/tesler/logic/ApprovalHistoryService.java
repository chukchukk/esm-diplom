package io.demo.service.tesler.logic;

import io.demo.dto.ApprovalHistoryDTO;
import io.demo.entity.ApprovalHistory;
import io.demo.entity.ApprovalHistory_;
import io.demo.service.tesler.meta.ApprovalHistoryMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.model.core.entity.BaseEntity_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApprovalHistoryService extends VersionAwareResponseService<ApprovalHistoryDTO, ApprovalHistory> {


	public ApprovalHistoryService() {
		super(ApprovalHistoryDTO.class, ApprovalHistory.class, null, ApprovalHistoryMeta.class);
	}

	@Override
	protected CreateResult<ApprovalHistoryDTO> doCreateEntity(ApprovalHistory entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<ApprovalHistoryDTO> doUpdateEntity(ApprovalHistory entity, ApprovalHistoryDTO data, BusinessComponent bc) {
		return null;
	}

	@Override
	protected Specification<ApprovalHistory> getSpecification(BusinessComponent bc) {
		return super.getSpecification(bc).and(
				(root, cq, cb) -> cb.equal(root.get(ApprovalHistory_.REQUEST_DATA).get(BaseEntity_.ID), bc.getParentIdAsLong())
		);
	}
}
