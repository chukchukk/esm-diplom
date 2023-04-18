package io.demo.conf.tesler.file;

import io.demo.entity.EsmFile;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.tesler.core.file.dto.FileDownloadDto;
import io.tesler.core.file.service.TeslerFileService;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

@Getter
@RequiredArgsConstructor
@Slf4j
public class TeslerFileServiceMinio implements TeslerFileService {

	public static final String FILENAME_FIELD = "filename";

	private static final String TEMPORARY_PREFIX = "temporary";

	private static final String DEFAULT_PREFIX = "esm";

	private final MinioClient minioClient;

	private final String tempBucketName;

	private final String defaultBucketName;

	@Override
	@SneakyThrows
	public String upload(@NonNull FileDownloadDto file, @Nullable String source) {
		String prefix;
		String bucket;
		if (source == null) {
			prefix = TEMPORARY_PREFIX;
			bucket = tempBucketName;
		} else {
			prefix = DEFAULT_PREFIX;
			bucket = source;
		}
		ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs
				.builder()
				.bucket(bucket)
				.object(prefix + UUID.randomUUID())
				.contentType(file.getType())
				.userMetadata(Collections.singletonMap(FILENAME_FIELD, file.getName()))
				.stream(new ByteArrayInputStream(file.getBytes()), file.getBytes().length, -1)
				.build()
		);
		return objectWriteResponse.object();
	}

	@Override
	@SneakyThrows
	public FileDownloadDto download(@NonNull String id, @Nullable String source) {
		String bucket;
		if (isTempFile(id)) {
			bucket = tempBucketName;
		} else {
			bucket = defaultBucketName;
		}
		GetObjectResponse response = minioClient.getObject(GetObjectArgs
				.builder()
				.bucket(bucket)
				.object(id)
				.build()
		);
		StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs
				.builder()
				.bucket(bucket)
				.object(id)
				.build()
		);
		return new FileDownloadDto(
				IOUtils.toByteArray(response),
				statObjectResponse.userMetadata().get(FILENAME_FIELD),
				statObjectResponse.contentType()
		);
	}

	@Override
	@SneakyThrows
	public void remove(@NonNull String id, @Nullable String source) {
		minioClient.removeObject(RemoveObjectArgs
				.builder()
				.bucket(tempBucketName)
				.object(id)
				.build()
		);
	}

	public Optional<FileDownloadDto> downloadIfExists(EsmFile esmFile) {
		try {
			FileDownloadDto fileDownloadDto = download(esmFile);
			return Optional.of(fileDownloadDto);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return Optional.empty();
	}

	public Optional<EsmFile> moveToDefaultBucket(@Nullable String id) {
		if (id == null) {
			return Optional.empty();
		}
		if (!isTempFile(id)) {
			throw new IllegalStateException("Only temp file can be moved to default file storage.");
		}
		FileDownloadDto downloadTempDto = download(id, null);
		String newID = upload(downloadTempDto, defaultBucketName);

		EsmFile esmFile = new EsmFile();
		esmFile.setId(newID);
		esmFile.setName(downloadTempDto.getName());

		if (isTempFile(id)) {
			remove(id, null);
		}
		return Optional.of(esmFile);
	}

	private FileDownloadDto download(EsmFile esmFile) {
		return download(esmFile.getId(), null);
	}

	private boolean isTempFile(String id) {
		return id.startsWith(TEMPORARY_PREFIX);
	}

}
