package io.demo.service.common;

import com.google.common.io.Files;
import io.demo.conf.tesler.file.TeslerFileServiceMinio;
import io.demo.entity.EsmFile;
import io.demo.entity.RequestDataFile;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataFileRepository;
import io.tesler.core.file.dto.FileDownloadDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class ZipService {

	private final RequestDataFileRepository requestDataFileRepository;

	private final TeslerFileServiceMinio fileServiceMinio;

	@SneakyThrows
	public ByteArrayOutputStream generateZipWithRequestDataFiles(RequestData requestData, Boolean byEmployee) {
		List<EsmFile> files = requestDataFileRepository.findAllByRequestDataAndAddedByEmployeeAndActiveIsTrueOrderByCreatedDateDesc(requestData, byEmployee)
				.stream()
				.map(RequestDataFile::getFile)
				.collect(Collectors.toList());
		List<FileDownloadDto> resultFiles = new ArrayList<>();

		for (EsmFile file : files) {
			Optional<FileDownloadDto> dto = fileServiceMinio.downloadIfExists(file);
			dto.ifPresent(resultFiles::add);
		}

		try (ByteArrayOutputStream archiveOutputStream = new ByteArrayOutputStream();
			ZipOutputStream zipOutputStream = new ZipOutputStream(archiveOutputStream)) {
			addFilesToZip(zipOutputStream, resultFiles);
			return archiveOutputStream;
		}
	}

	@SneakyThrows
	private void addFilesToZip(@NonNull ZipOutputStream zipOutputStream, List<FileDownloadDto> files) {
		Map<String, Integer> zipFileNameMap = new HashMap<>();
		for (FileDownloadDto dto : files) {
			if (dto != null) {
				String resultFileNameWithExtension;
				String fileName = Files.getNameWithoutExtension(dto.getName());
				String fileExtension = Files.getFileExtension(dto.getName());
				if (zipFileNameMap.containsKey(fileName)) {
					resultFileNameWithExtension = fileName + "(" + zipFileNameMap.get(fileName) + ")." + fileExtension;
				} else {
					resultFileNameWithExtension = dto.getName();
				}
				zipFileNameMap.merge(Files.getNameWithoutExtension(dto.getName()), 1, Integer::sum);

				ZipEntry entry = new ZipEntry(resultFileNameWithExtension);
				zipOutputStream.putNextEntry(entry);
				zipOutputStream.write(dto.getBytes());
				zipOutputStream.closeEntry();
			}
		}
	}

}
