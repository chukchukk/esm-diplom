package io.demo.repository;

import io.tesler.api.data.dictionary.LOV;
import io.tesler.model.core.entity.User;
import io.tesler.model.core.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

	Optional<UserRole> findByUserAndInternalRoleCd(User user, LOV role);

}
