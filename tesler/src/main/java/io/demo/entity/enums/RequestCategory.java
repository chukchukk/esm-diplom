package io.demo.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import io.demo.service.common.PreActions;
import io.demo.service.portal.builder.*;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.PreAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@AllArgsConstructor
@Getter
public enum RequestCategory {

	DOCUMENT("Получение справок и копий документов",
			DocumentCategoryDictionaryKeyValuesBuilder.class,
			pair -> Boolean.TRUE.equals(((DocumentCategoryRequestData) pair.getLeft()).getPaperCopy()) ?
					PreActions.showCompleteRequestPopup(pair.getRight()) : PreActions.showCompleteRequestWithFilePopup(pair.getRight()),
			true),
	PERSONAL_DATA_CHANGE("Изменение персональных данных",
			PersonalDataChangeCategoryDictionaryKeyValuesBuilder.class,
			pair -> PreActions.showCompleteRequestPopup(pair.getRight()),
			true),
	ADMINISTRATIVE_SUPPORT("Административная и техническая поддержка",
			AdministrativeSupportCategoryDictionaryKeyValuesBuilder.class,
			null,
			false),
	IT_SERVICE("ИТ сервисы",
			ItServiceCategoryDictionaryKeyValuesBuilder.class,
			null,
			false);

	@JsonValue
	private final String value;

	private final Class<? extends DictionaryKeyValuesAbstractBuilder> dictionariesBuilderClass;

	private final Function<Pair<RequestData, BusinessComponent>, PreAction> completePreActionProvider;

	private final Boolean active;

	public static List<RequestCategory> getActiveCategories() {
		return Arrays.stream(RequestCategory.values()).filter(RequestCategory::getActive).collect(Collectors.toList());
	}

}
