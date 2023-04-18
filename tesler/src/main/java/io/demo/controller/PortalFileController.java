package io.demo.controller;

import io.demo.dto.RequestDataFileDTO;
import io.demo.service.portal.PortalFileService;
import io.tesler.core.file.dto.FileDownloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PortalFileController {

	private final PortalFileService portalFileService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/uploadRequestDataFiles/{id}")
	public ResponseEntity<List<RequestDataFileDTO>> uploadRequestDataFiles(@RequestPart(name = "files") List<MultipartFile> files, @PathVariable(name = "id") Long requestId) {
		List<RequestDataFileDTO> result = portalFileService.uploadRequestDataFiles(files, requestId);
		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/uploadRequestDataFile/{id}")
	public ResponseEntity<List<RequestDataFileDTO>> uploadRequestDataFile(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") Long requestId) {
		List<RequestDataFileDTO> result = portalFileService.uploadRequestDataFile(file, requestId);
		return ResponseEntity.ok(result);
	}


	@GetMapping("/downloadFile")
	public HttpEntity<byte[]> download(@RequestParam("id") String id,
										@RequestParam(value = "source", required = false) String source,
										@RequestParam(value = "preview", required = false, defaultValue = "false") boolean preview) {
		FileDownloadDto file = portalFileService.download(id, source);
		return buildFileHttpEntity(file.getBytes(), file.getName(), file.getType(), preview);
	}

	@GetMapping(value = "/zip", produces = "application/zip")
	public HttpEntity<byte[]> downloadZip(@RequestParam("requestId") Long requestId,
											@RequestParam("byEmployee") Boolean byEmployee) {
		byte[] zipBytes = portalFileService.generateZipWithRequestDataFiles(requestId, byEmployee);
		return buildFileHttpEntity(zipBytes, "files.zip", "application/zip", false);
	}

	@DeleteMapping("/deleteFile")
	public ResponseEntity<List<RequestDataFileDTO>> deleteFile(@RequestParam(name = "requestId") Long requestId,
																@RequestParam(name = "fileName") String fileName,
																@RequestParam(name = "fileId") String fileId) {
		return ResponseEntity.ok(portalFileService.deleteFile(requestId, fileName, fileId));
	}

	private HttpEntity<byte[]> buildFileHttpEntity(byte[] content, String fileName, String fileType, boolean inline) {
		HttpHeaders header = new HttpHeaders();
		header.set(
				HttpHeaders.CONTENT_DISPOSITION,
				ContentDisposition.builder(inline ? "inline" : "attachment")
						.filename(fileName, StandardCharsets.UTF_8)
						.build()
						.toString()
		);
		header.setContentType(getMediaType(fileType));
		header.setContentLength(content.length);
		return new HttpEntity<>(content, header);
	}

	private MediaType getMediaType(final String type) {
		try {
			return MediaType.parseMediaType(type);
		} catch (InvalidMediaTypeException e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}


}
