package io.demo.repository;

import io.demo.entity.UserLegalEntity;
import io.tesler.model.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserLegalEntityRepository extends JpaRepository<UserLegalEntity, Long>, JpaSpecificationExecutor<UserLegalEntity> {

	List<UserLegalEntity> findAllByUser(User user);

	Optional<UserLegalEntity> findByUserAndIsDefaultIsTrue(User user);
}
