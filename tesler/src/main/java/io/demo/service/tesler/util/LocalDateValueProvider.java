package io.demo.service.tesler.util;

import io.tesler.api.data.dto.rowmeta.FieldDTO;
import io.tesler.core.controller.param.FilterParameter;
import io.tesler.core.dao.ClassifyDataParameter;
import io.tesler.core.exception.ClientException;
import io.tesler.core.util.filter.SearchParameter;
import io.tesler.core.util.filter.provider.ClassifyDataProvider;
import io.tesler.core.util.filter.provider.impl.AbstractClassifyDataProvider;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.tesler.api.util.i18n.ErrorMessageSource.errorMessage;
import static io.tesler.core.controller.param.SearchOperation.CONTAINS_ONE_OF;
import static io.tesler.core.controller.param.SearchOperation.EQUALS_ONE_OF;

@Component
@EqualsAndHashCode(callSuper = false)
public class LocalDateValueProvider extends AbstractClassifyDataProvider implements ClassifyDataProvider {

	@Override
	protected List<ClassifyDataParameter> getProviderParameterValues(Field dtoField, ClassifyDataParameter dataParameter,
																		FilterParameter filterParam, SearchParameter searchParam,
																		List<ClassifyDataProvider> providers) {
		List<ClassifyDataParameter> result;
		if (CONTAINS_ONE_OF.equals(dataParameter.getOperator()) || EQUALS_ONE_OF.equals(dataParameter.getOperator())) {
			throw new ClientException(errorMessage("error.unsupported_type_filtration", LocalDateTime.class));
		}
		result = new ArrayList<>();
		setClassifyDateParameterDateValue(dataParameter, filterParam, FieldDTO.isTzAware(dtoField), searchParam, result);
		dataParameter.setValue(((LocalDateTime) dataParameter.getValue()).toLocalDate());
		result.add(dataParameter);
		return result;
	}

}