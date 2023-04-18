package io.demo.repository;

import io.demo.entity.ApprovalHistory;
import io.demo.entity.requestdatabytype.RequestData;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Long>, JpaSpecificationExecutor<ApprovalHistory> {

	List<ApprovalHistory> findAllByRequestDataOrderByCreatedDateDesc(@NonNull RequestData requestData);
}
