package io.demo.repository;

import io.demo.entity.EsmFile;
import io.demo.entity.RequestDataFile;
import io.demo.entity.requestdatabytype.RequestData;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestDataFileRepository extends JpaRepository<RequestDataFile, Long>, JpaSpecificationExecutor<RequestDataFile> {

	List<RequestDataFile> findAllByRequestDataAndActiveIsTrueOrderByCreatedDateDesc(@NonNull RequestData requestData);

	List<RequestDataFile> findAllByRequestDataAndAddedByEmployeeAndActiveIsTrueOrderByCreatedDateDesc(@NonNull RequestData requestDataFile,
																										Boolean addedByEmployee);

	@Transactional
	@Modifying
	@Query("UPDATE RequestDataFile rdf " +
			"SET rdf.active = false " +
			"where rdf.file = :file and rdf.requestData = :requestData")
	void deactivateByFileAndRequestData(EsmFile file, RequestData requestData);
}
