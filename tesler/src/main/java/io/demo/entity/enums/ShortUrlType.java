package io.demo.entity.enums;

import io.demo.conf.shorturl.ShortUrlProperties;
import io.demo.entity.ShortUrl;
import io.demo.entity.requestdatabytype.RequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
public enum ShortUrlType {

	FORM_REQUEST(RequestData.class, (ent, prop) -> Optional.of(prop.getPortalUrl() + UiUrl.PORTAL_REQUEST_FORM + ent.getEntityId()));

	private final Class entityClass;

	private final BiFunction<ShortUrl, ShortUrlProperties, Optional<String>> restoreOriginalUrl;

	private static final class UiUrl {
		public static final String PORTAL_REQUEST_FORM = "my-requests/";
	}

}
