package io.demo.repository;

import io.demo.entity.RequestDataTypeOption;
import io.demo.entity.enums.RequestCategory;
import io.demo.entity.enums.RequestType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestTypeOptionRepository extends JpaRepository<RequestDataTypeOption, Long>, JpaSpecificationExecutor<RequestDataTypeOption> {

	@Query(value = "SELECT r from RequestDataTypeOption r where r.category = :category")
	List<RequestDataTypeOption> getAllOptionsByCategory(@NonNull RequestCategory category);

	List<RequestDataTypeOption> getAllByRequestType(@NonNull RequestType type);

}
