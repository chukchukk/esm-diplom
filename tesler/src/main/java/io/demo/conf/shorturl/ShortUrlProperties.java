package io.demo.conf.shorturl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("app.shorturl")
public class ShortUrlProperties {

	private String teslerUrl;

	private String portalUrl;

	private String redirectUrl;

}
