package io.demo.conf;

import io.tesler.api.config.TeslerBeanProperties;
import io.tesler.api.service.tx.ITransactionStatus;
import io.tesler.core.config.APIConfig;
import io.tesler.core.config.CoreApplicationConfig;
import io.tesler.core.config.UIConfig;
import io.tesler.model.core.config.PersistenceJPAConfig;
import io.tesler.model.core.tx.TeslerJpaTransactionManagerForceActiveAware;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.jdbc.PostgreSqlJdbcIndexedSessionRepositoryCustomizer;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
@Import({
		CoreApplicationConfig.class,
		PersistenceJPAConfig.class,
		UIConfig.class,
		APIConfig.class
})
@EnableJdbcHttpSession(cleanupCron = "0 0/10 * * * ?")
@EnableJpaRepositories(basePackages = "io.demo")
@EntityScan({"io.tesler", "io.demo"})
@EnableAsync
public class ApplicationConfig {

	public static final int USERS_THREAD_POOL = 100;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedMethods("*")
						.allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}

	@Bean
	public PostgreSqlJdbcIndexedSessionRepositoryCustomizer postgreSqlJdbcIndexedSessionRepositoryCustomizer() {
		return new PostgreSqlJdbcIndexedSessionRepositoryCustomizer();
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			final ApplicationContext applicationContext,
			final TeslerBeanProperties teslerBeanProperties,
			final ITransactionStatus txStatus) {
		return new TeslerJpaTransactionManagerForceActiveAware(applicationContext, teslerBeanProperties, txStatus);
	}

	@Bean(name = "esmThreadPool")
	public Executor esmThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(USERS_THREAD_POOL);
		executor.setMaxPoolSize(USERS_THREAD_POOL);
		executor.setQueueCapacity(USERS_THREAD_POOL * 2);
		return executor;
	}

	@Bean
	public RestTemplate restTemplate(
			RestTemplateBuilder builder,
			LogbookClientHttpRequestInterceptor interceptor) {
		return builder
				.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
				.additionalInterceptors(interceptor)
				.build();
	}

}
