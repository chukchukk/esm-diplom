package io.demo.repository;

import io.demo.entity.ShortUrl;
import io.demo.entity.enums.ShortUrlType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long>, JpaSpecificationExecutor<ShortUrl> {

	Optional<ShortUrl> findByUuid(@NonNull String uuid);

	ShortUrl getByEntityIdAndType(Long id, ShortUrlType type);

}
