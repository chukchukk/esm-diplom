package io.demo.conf.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@Setter
@Validated
@ConfigurationProperties("app.mail")
public class EmailProperties {

	@NotBlank
	private String from;

	private Integer batchSize;

	private Map<Long, String> divisionEmails;

}
