package io.demo.repository;

import io.demo.entity.enums.RequestStatus;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.RequestData_;
import io.tesler.model.core.entity.User;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RequestDataRepository extends JpaRepository<RequestData, Long>, JpaSpecificationExecutor<RequestData> {

	@Query("SELECT DISTINCT rd.implementer.email FROM RequestData rd WHERE rd.actualDeadline <= CURRENT_DATE AND rd.status IN :statusList AND rd.implementer.email IS NOT NULL")
	List<String> findAllImplementerEmailWithExpiringDeadline(List<RequestStatus> statusList);

	List<RequestData> findAllByStatusAndActualDeadlineGreaterThanEqual(RequestStatus requestStatus, LocalDate date);

	Optional<RequestData> findByIdAndUser(Long id, User user);

	default Page<RequestData> findAllByUserAndActive(@NonNull User user, @NonNull Boolean isActive, @NonNull Pageable pageable) {
		return findAll(Specification.where(userEquals(user)).and(statusActivityEquals(isActive)), pageable);
	}

	@NotNull
	default Specification<RequestData> userEquals(User user) {
		return (root, query, cb) -> cb.equal(
				root.get(RequestData_.user), user
		);
	}

	@NotNull
	default Specification<RequestData> statusActivityEquals(Boolean isActive) {
		return (root, query, cb) -> root.get(RequestData_.status).in(RequestStatus.getStatusesByIsActive(isActive));
	}

}
