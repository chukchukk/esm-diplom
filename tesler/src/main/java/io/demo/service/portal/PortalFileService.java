package io.demo.service.portal;

import io.demo.conf.tesler.file.TeslerFileServiceMinio;
import io.demo.dto.RequestDataFileDTO;
import io.demo.entity.EsmFile;
import io.demo.entity.RequestDataFile;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataFileRepository;
import io.demo.repository.RequestDataRepository;
import io.demo.service.common.ZipService;
import io.tesler.core.file.dto.FileDownloadDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortalFileService {

	private final TeslerFileServiceMinio fileServiceMinio;

	private final RequestDataRepository requestDataRepository;

	private final RequestDataFileRepository requestDataFileRepository;

	private final ZipService zipService;

	public List<RequestDataFileDTO> uploadRequestDataFiles(List<MultipartFile> files, Long requestId) {
		List<RequestDataFile> esmFiles = new ArrayList<>();
		RequestData requestData = requestDataRepository.getById(requestId);

		for (MultipartFile file : files) {
			String fileId = fileServiceMinio.upload(file, fileServiceMinio.getDefaultBucketName());
			esmFiles.add(
					new RequestDataFile()
							.setRequestData(requestData)
							.setAddedByEmployee(true)
							.setFile(
									new EsmFile()
											.setId(fileId)
											.setName(file.getOriginalFilename())
							)
							.setActive(true)
			);
		}

		requestDataFileRepository.saveAll(esmFiles);
		return requestDataFileRepository.findAllByRequestDataAndAddedByEmployeeAndActiveIsTrueOrderByCreatedDateDesc(requestData, true)
				.stream().map(RequestDataFileDTO::new).collect(Collectors.toList());
	}

	public List<RequestDataFileDTO> uploadRequestDataFile(MultipartFile file, Long requestId) {
		RequestData requestData = requestDataRepository.getById(requestId);
		List<RequestDataFile> oldFiles = requestDataFileRepository.findAllByRequestDataAndAddedByEmployeeAndActiveIsTrueOrderByCreatedDateDesc(requestData, true);
		String fileId = fileServiceMinio.upload(file, fileServiceMinio.getDefaultBucketName());
		RequestDataFile requestDataFile = new RequestDataFile()
				.setRequestData(requestData)
				.setAddedByEmployee(true)
				.setFile(new EsmFile()
						.setId(fileId)
						.setName(file.getName()))
				.setActive(true);

		requestDataFileRepository.save(requestDataFile);
		oldFiles.add(requestDataFile);
		return oldFiles.stream().map(RequestDataFileDTO::new)
				.collect(Collectors.toList());
	}

	public FileDownloadDto download(String id, String source) {
		return fileServiceMinio.download(id, source);
	}

	public List<RequestDataFileDTO> deleteFile(@NonNull Long requestId, @NonNull String fileName, @NonNull String fileId) {
		EsmFile esmFile = new EsmFile()
				.setId(fileId)
				.setName(fileName);
		RequestData requestData = requestDataRepository.getById(requestId);
		requestDataFileRepository.deactivateByFileAndRequestData(esmFile, requestData);
		return requestDataFileRepository.findAllByRequestDataAndAddedByEmployeeAndActiveIsTrueOrderByCreatedDateDesc(requestData, true)
				.stream().map(RequestDataFileDTO::new)
				.collect(Collectors.toList());
	}

	@SneakyThrows
	public byte[] generateZipWithRequestDataFiles(Long requestId, Boolean byEmployee) {
		RequestData requestData = requestDataRepository.getById(requestId);
		return zipService.generateZipWithRequestDataFiles(requestData, byEmployee).toByteArray();
	}

}
