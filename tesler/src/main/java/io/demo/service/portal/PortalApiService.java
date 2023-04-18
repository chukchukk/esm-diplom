package io.demo.service.portal;

import io.demo.service.common.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalApiService {

	private final ShortUrlService shortUrlService;

	public String getExtLink(String uuid) {
		return shortUrlService
				.restoreOriginalUrlByShortUrlUUID(uuid)
				.orElse(shortUrlService.createPageNotFoundUrl());
	}

}
