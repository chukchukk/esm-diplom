package io.demo.service.portal.builder;

import io.demo.dto.portal.requestcreate.DictionaryKeyValueDTO;
import io.demo.entity.UserLegalEntity;
import io.demo.entity.enums.*;
import io.demo.repository.UserLegalEntityRepository;
import io.demo.service.common.ESMUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItServiceCategoryDictionaryKeyValuesBuilder extends DictionaryKeyValuesAbstractBuilder {

	public ItServiceCategoryDictionaryKeyValuesBuilder(ESMUserRoleService userRoleService, UserLegalEntityRepository userLegalEntityRepository) {
		super(userRoleService, userLegalEntityRepository);
	}

	@Override
	public Map<String, List<DictionaryKeyValueDTO>> buildFieldNameToDictionariesMap() {
		return Map.of(
				Option.FieldNameConstant.TYPE, mapValuesToDictionaryStringKeyValue(RequestType.getAllKeyMarkersByCategory(RequestCategory.IT_SERVICE)),
				Option.FieldNameConstant.USER_COMPANY, mapValuesToDictionaryStringKeyValue(
						userLegalEntityRepository.findAllByUser(userRoleService.getCurrentUser())
								.stream()
								.map(UserLegalEntity::getLegalEntity)
								.collect(Collectors.toList())
				)
		);
	}

}
