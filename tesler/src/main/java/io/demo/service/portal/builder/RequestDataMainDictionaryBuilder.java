package io.demo.service.portal.builder;

import io.demo.entity.enums.RequestCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestDataMainDictionaryBuilder {

	private final List<DictionaryKeyValuesAbstractBuilder> builders;

	public DictionaryKeyValuesAbstractBuilder getInstance(RequestCategory requestCategory) {
		return builders.stream()
				.filter(builder -> builder.getClass().equals(requestCategory.getDictionariesBuilderClass()))
				.findFirst()
				.orElseThrow();
	}

}
