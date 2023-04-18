package io.demo.service.tesler.logic;

import io.demo.conf.tesler.file.TeslerFileServiceMinio;
import io.demo.dto.RequestWriteFileDTO;
import io.demo.entity.RequestDataFile;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.repository.RequestDataFileRepository;
import io.demo.repository.RequestDataRepository;
import io.demo.service.tesler.meta.RequestWriteFileMeta;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.dto.rowmeta.ActionResultDTO;
import io.tesler.core.dto.rowmeta.CreateResult;
import io.tesler.core.service.action.ActionScope;
import io.tesler.core.service.action.Actions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestWriteFileService extends VersionAwareResponseService<RequestWriteFileDTO, RequestDataFile> {

	private final RequestDataRepository requestDataRepository;

	private final TeslerFileServiceMinio fileService;

	private final RequestDataFileRepository requestDataFileRepository;

	public RequestWriteFileService(RequestDataRepository requestDataRepository, TeslerFileServiceMinio fileService, RequestDataFileRepository requestDataFileRepository) {
		super(RequestWriteFileDTO.class, RequestDataFile.class, null, RequestWriteFileMeta.class);
		this.requestDataRepository = requestDataRepository;
		this.fileService = fileService;
		this.requestDataFileRepository = requestDataFileRepository;
	}

	@Override
	protected CreateResult<RequestWriteFileDTO> doCreateEntity(RequestDataFile entity, BusinessComponent bc) {
		return null;
	}

	@Override
	protected ActionResultDTO<RequestWriteFileDTO> doUpdateEntity(RequestDataFile entity, RequestWriteFileDTO data, BusinessComponent bc) {
		return null;
	}

	@Override
	public Actions<RequestWriteFileDTO> getActions() {
		return Actions.<RequestWriteFileDTO>builder()
				.action("file-upload", "Добавить файлы")
				.available(bc -> true)
				.scope(ActionScope.BC).add()
				.action("file-upload-save", "File Upload Save")
				.scope(ActionScope.BC)
				.invoker(this::fileUpload)
				.add()
				.build();
	}

	private ActionResultDTO<RequestWriteFileDTO> fileUpload(BusinessComponent bc, RequestWriteFileDTO data) {
		RequestData requestData = requestDataRepository.getById(bc.getParentIdAsLong());
		List<RequestDataFile> requestDataFiles = new ArrayList<>();

		Optional.ofNullable(data.getBulkIds()).ifPresent(res -> res.forEach(item -> {
			RequestDataFile file = new RequestDataFile()
					.setRequestData(requestData)
					.setFile(fileService.moveToDefaultBucket(item).orElse(null))
					.setAddedByEmployee(true)
					.setActive(true);
			requestDataFiles.add(file);
		}));
		requestDataFileRepository.saveAll(requestDataFiles);
		return new ActionResultDTO<>();
	}

}
