package io.demo.repository;

import io.demo.entity.DualView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DualViewRepository extends JpaRepository<DualView, Long> {

	@Query("SELECT d FROM DualView d")
	DualView getFirst();

}
