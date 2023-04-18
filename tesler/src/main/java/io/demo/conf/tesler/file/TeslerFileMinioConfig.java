package io.demo.conf.tesler.file;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TeslerFileMinioConfig {

	@Bean
	@Primary
	public TeslerFileServiceMinio fileService(
			MinioClient minioClient,
			@Value("${minio.tempbucket.name}") String tempBucketName,
			@Value("${minio.bucket.name}") String defaultBucketName) {
		return new TeslerFileServiceMinio(minioClient, tempBucketName, defaultBucketName);
	}



	@Bean
	public MinioClient minioClient(
			@Value("${minio.access.name}") String accessKey,
			@Value("${minio.access.secret}") String accessSecret,
			@Value("${minio.url}") String minioUrl) {
		return MinioClient.builder()
				.endpoint(minioUrl)
				.credentials(accessKey, accessSecret)
				.build();
	}

}
