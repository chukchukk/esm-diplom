package io.demo.mapper;

import io.demo.dto.RequestFormPortalDTO;
import io.demo.dto.RequestTypeOptionDTO;
import io.demo.entity.RequestDataTypeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestDataTypeOptionMapper {

	RequestDataTypeOptionMapper INSTANCE = Mappers.getMapper(RequestDataTypeOptionMapper.class);

	RequestTypeOptionDTO toRequestTypeOptionDTO(RequestDataTypeOption data);

	@Mapping(target = "value", expression = "java(data.getOption().getTitle())")
	@Mapping(target = "type", expression = "java(data.getOption().getFieldType().getValue())")
	@Mapping(target = "visible", expression = "java(data.getOption().getIsUICardBodyVisible())")
	@Mapping(target = "isUICardAction", expression = "java(data.getOption().getIsUICardAction())")
	RequestFormPortalDTO.FieldUIValue toFieldUIValue(RequestDataTypeOption data);
}
