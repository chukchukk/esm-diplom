package io.demo.service.common;

import io.demo.conf.shorturl.ShortUrlProperties;
import io.demo.entity.ShortUrl;
import io.demo.entity.enums.ShortUrlType;
import io.demo.repository.ShortUrlRepository;
import io.tesler.core.dto.MessageType;
import io.tesler.core.dto.rowmeta.PostAction;
import io.tesler.core.exception.BusinessException;
import io.tesler.model.core.entity.BaseEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

	private final ShortUrlProperties shortUrlProperties;

	private final ShortUrlRepository shortUrlRepository;

	/**
	 * Используется метод flush(), т.к. метод вызывается из теслера и портала
	 */
	public String upsertShortUrl(@NonNull BaseEntity entity, ShortUrlType type) {
		ShortUrl shortUrl = shortUrlRepository.getByEntityIdAndType(entity.getId(), type);
		if (shortUrl == null) {
			shortUrl = new ShortUrl()
					.setUuid(UUID.randomUUID().toString())
					.setType(type)
					.setEntityId(entity.getId());
			try {
				shortUrlRepository.save(shortUrl);
				shortUrlRepository.flush();
			} catch (Exception e) {
				throw new BusinessException().addPostAction(PostAction.showMessage(
								MessageType.ERROR,
								"Попробуйте еще раз"))
						.addPopup("Попробуйте еще раз");
			}
		}
		return shortUrlProperties.getTeslerUrl() + shortUrlProperties.getRedirectUrl() + shortUrl.getUuid();
	}

	public Optional<String> restoreOriginalUrlByShortUrlUUID(String uuid) {
		ShortUrl shortUrl = shortUrlRepository.findByUuid(uuid).orElse(null);
		if (shortUrl == null) {
			return Optional.empty();
		}
		return shortUrl.getType().getRestoreOriginalUrl().apply(shortUrl, shortUrlProperties);
	}

	public String createPageNotFoundUrl() {
		return shortUrlProperties.getPortalUrl() + "notFound";
	}

}
