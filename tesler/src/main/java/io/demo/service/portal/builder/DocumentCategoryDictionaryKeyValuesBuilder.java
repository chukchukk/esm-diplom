package io.demo.service.portal.builder;

import io.demo.dto.portal.requestcreate.DictionaryKeyValueDTO;
import io.demo.dto.portal.requestcreate.LegalEntityKeyValueDTO;
import io.demo.entity.KeyValueMarker;
import io.demo.entity.UserLegalEntity;
import io.demo.entity.enums.*;
import io.demo.repository.UserLegalEntityRepository;
import io.demo.service.common.ESMUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DocumentCategoryDictionaryKeyValuesBuilder extends DictionaryKeyValuesAbstractBuilder {

	public DocumentCategoryDictionaryKeyValuesBuilder(ESMUserRoleService userRoleService, UserLegalEntityRepository userLegalEntityRepository) {
		super(userRoleService, userLegalEntityRepository);
	}

	@Override
	public Map<String, List<DictionaryKeyValueDTO>> buildFieldNameToDictionariesMap() {
		return Map.of(
				Option.FieldNameConstant.LANGUAGE, mapValuesToDictionaryStringKeyValue(List.of(Language.values())),
				Option.FieldNameConstant.USER_COMPANY, mapValuesToLegalEntityKeyValueDTO(
						userLegalEntityRepository.findAllByUser(userRoleService.getCurrentUser())),
				Option.FieldNameConstant.TYPE, mapValuesToDictionaryStringKeyValue(RequestType.getAllKeyMarkersByCategory(RequestCategory.DOCUMENT)),
				Option.FieldNameConstant.WHERE_REFERENCE_EC, mapValuesToDictionaryStringKeyValue(WhereReference.getKeyMarkersByRequestType(RequestType.EMPLOYMENT_CERTIFICATE)),
				Option.FieldNameConstant.WHERE_REFERENCE_WC, mapValuesToDictionaryStringKeyValue(WhereReference.getKeyMarkersByRequestType(RequestType.WORK_CONTRACT)),
				Option.FieldNameConstant.WHERE_REFERENCE_WB, mapValuesToDictionaryStringKeyValue(WhereReference.getKeyMarkersByRequestType(RequestType.WORK_BOOK))
		);
	}

	private List<DictionaryKeyValueDTO> mapValuesToLegalEntityKeyValueDTO(List<UserLegalEntity> userLegalEntities) {
		List<DictionaryKeyValueDTO> dtos = new ArrayList<>();
		userLegalEntities
				.forEach(userLegalEntity -> {
							KeyValueMarker legalEntity = userLegalEntity.getLegalEntity();
							dtos.add(new LegalEntityKeyValueDTO(
									legalEntity.getKey(), legalEntity.getValue(),
									userLegalEntity.getIsDefault()
							));
						}
				);
		return dtos;
	}

}
