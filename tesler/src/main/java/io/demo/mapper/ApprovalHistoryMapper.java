package io.demo.mapper;

import io.demo.dto.ApprovalHistoryDTO;
import io.demo.entity.ApprovalHistory;
import io.demo.repository.UserRepository;
import io.tesler.model.core.entity.User;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(imports = {Optional.class, User.class, UserRepository.class})
public interface ApprovalHistoryMapper {

	ApprovalHistoryMapper INSTANCE = Mappers.getMapper(ApprovalHistoryMapper.class);

	@Mapping(target = "actionAuthor", expression = "java(data.getChangedByUserFullName())")
	@Mapping(target = "actionAuthorId", expression = "java(userRepository.findById(data.getLastUpdBy()).map(User::getId).orElse(null))")
	ApprovalHistoryDTO toApprovalHistoryDTO(@NonNull ApprovalHistory data, UserRepository userRepository);

	@Mapping(target = "actionAuthor", expression = "java(data.getChangedByUserFullName())")
	@Mapping(target = "color", expression = "java(data.getStatus().getColor().getValue())")
	void fromApprovalHistoryEntity(@NonNull ApprovalHistory data, @MappingTarget ApprovalHistoryDTO dto);

}
