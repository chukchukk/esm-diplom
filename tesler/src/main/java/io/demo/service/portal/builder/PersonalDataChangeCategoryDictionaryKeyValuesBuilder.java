package io.demo.service.portal.builder;

import io.demo.dto.portal.requestcreate.DictionaryKeyValueDTO;
import io.demo.entity.enums.Option;
import io.demo.entity.enums.RequestCategory;
import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.SnilsChange;
import io.demo.repository.UserLegalEntityRepository;
import io.demo.service.common.ESMUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PersonalDataChangeCategoryDictionaryKeyValuesBuilder extends DictionaryKeyValuesAbstractBuilder {

	public PersonalDataChangeCategoryDictionaryKeyValuesBuilder(ESMUserRoleService userRoleService, UserLegalEntityRepository userLegalEntityRepository) {
		super(userRoleService, userLegalEntityRepository);
	}

	@Override
	public Map<String, List<DictionaryKeyValueDTO>> buildFieldNameToDictionariesMap() {
		return Map.of(
				Option.FieldNameConstant.CHANGE_SNILS, mapValuesToDictionaryStringKeyValue(List.of(SnilsChange.values())),
				Option.FieldNameConstant.TYPE, mapValuesToDictionaryStringKeyValue(RequestType.getAllKeyMarkersByCategory(RequestCategory.PERSONAL_DATA_CHANGE))
		);
	}

}
