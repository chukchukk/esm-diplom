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
public class AdministrativeSupportCategoryDictionaryKeyValuesBuilder extends DictionaryKeyValuesAbstractBuilder {

	public AdministrativeSupportCategoryDictionaryKeyValuesBuilder(ESMUserRoleService userRoleService, UserLegalEntityRepository userLegalEntityRepository) {
		super(userRoleService, userLegalEntityRepository);
	}

	@Override
	public Map<String, List<DictionaryKeyValueDTO>> buildFieldNameToDictionariesMap() {
		return Map.of(
				Option.FieldNameConstant.DELIVERY_TYPE, mapValuesToDictionaryStringKeyValue(List.of(DeliveryType.values())),
				Option.FieldNameConstant.USER_COMPANY, mapValuesToDictionaryStringKeyValue(
						userLegalEntityRepository.findAllByUser(userRoleService.getCurrentUser())
								.stream()
								.map(UserLegalEntity::getLegalEntity)
								.collect(Collectors.toList())
				),
				Option.FieldNameConstant.PARCEL_TYPE, mapValuesToDictionaryStringKeyValue(List.of(ParcelType.values())),
				Option.FieldNameConstant.TYPE, mapValuesToDictionaryStringKeyValue(RequestType.getAllKeyMarkersByCategory(RequestCategory.ADMINISTRATIVE_SUPPORT))
		);
	}

}
