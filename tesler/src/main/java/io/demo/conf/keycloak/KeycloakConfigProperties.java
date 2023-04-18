package io.demo.conf.keycloak;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Setter
@Getter
@ConfigurationProperties("app.backoffice")
public class KeycloakConfigProperties {

	private Map<String, Object> keycloak;

}