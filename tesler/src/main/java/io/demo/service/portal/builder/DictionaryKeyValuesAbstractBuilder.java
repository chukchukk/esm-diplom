package io.demo.service.portal.builder;

import io.demo.dto.portal.requestcreate.DictionaryKeyValueDTO;
import io.demo.entity.KeyValueMarker;
import io.demo.repository.UserLegalEntityRepository;
import io.demo.service.common.ESMUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public abstract class DictionaryKeyValuesAbstractBuilder {

	final ESMUserRoleService userRoleService;

	final UserLegalEntityRepository userLegalEntityRepository;

	protected List<DictionaryKeyValueDTO> mapValuesToDictionaryStringKeyValue(List<KeyValueMarker> valueMarkers) {
		List<DictionaryKeyValueDTO> dtos = new ArrayList<>();
		valueMarkers
				.forEach(vm ->
						dtos.add(new DictionaryKeyValueDTO(vm.getKey(), vm.getValue()))
				);
		return dtos;
	}

	public abstract Map<String, List<DictionaryKeyValueDTO>> buildFieldNameToDictionariesMap();

}
