package io.demo.mapper;

import io.demo.dto.RequestFormPortalDTO;
import io.demo.entity.enums.RequestCategory;
import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.WhereReference;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.category.DocumentCategoryRequestData;
import io.demo.entity.requestdatabytype.document.EmploymentCertificateRequestData;
import io.demo.entity.requestdatabytype.document.WorkBookRequestData;
import io.demo.entity.requestdatabytype.document.WorkContractRequestData;
import io.demo.entity.requestdatabytype.personaldatachange.RegistrationChangeRequestData;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {WhereReference.class})
public interface PortalFieldVisibilityMapper {

	PortalFieldVisibilityMapper INSTANCE = Mappers.getMapper(PortalFieldVisibilityMapper.class);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "visible", expression = "java(!data.getActualResidence())")
	void updateResidenceAddressVisibility(RegistrationChangeRequestData data, @MappingTarget RequestFormPortalDTO.FieldUIValue fieldUIValue);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "visible", expression = "java(data.getPaperCopy())")
	void updateDeliveryNeedVisibility(DocumentCategoryRequestData data, @MappingTarget RequestFormPortalDTO.FieldUIValue fieldUIValue);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "visible", expression = "java(getDeliveryAddressVisibility(data))")
	void updateDeliveryAddressVisibility(RequestData data, @MappingTarget RequestFormPortalDTO.FieldUIValue fieldUIValue);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "visible", expression = "java(getWhereReferenceOtherVisibility(data))")
	void updateWhereReferenceOtherVisibility(RequestData data, @MappingTarget RequestFormPortalDTO.FieldUIValue fieldUIValue);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "visible", expression = "java(WhereReference.EMBASSY.equals(data.getWhereReference()))")
	void updateTravelFieldVisibility(EmploymentCertificateRequestData data, @MappingTarget RequestFormPortalDTO.FieldUIValue fieldUIValue);

	default Boolean getDeliveryAddressVisibility(RequestData data) {
		if (RequestCategory.DOCUMENT.equals(data.getType().getCategory())) {
			DocumentCategoryRequestData documentCategoryRequestData = (DocumentCategoryRequestData) data;
			return documentCategoryRequestData.getPaperCopy() && documentCategoryRequestData.getDeliveryNeed();
		}
		return false;
	}

	default Boolean getWhereReferenceOtherVisibility(RequestData data) {
		if (RequestType.EMPLOYMENT_CERTIFICATE.equals(data.getType())) {
			EmploymentCertificateRequestData employmentCertificateRequestData = (EmploymentCertificateRequestData) data;
			return WhereReference.OTHER.equals(employmentCertificateRequestData.getWhereReference());
		} else if (RequestType.WORK_BOOK.equals(data.getType())) {
			WorkBookRequestData workBookRequestData = (WorkBookRequestData) data;
			return WhereReference.OTHER.equals(workBookRequestData.getWhereReference());
		} else if (RequestType.WORK_CONTRACT.equals(data.getType())) {
			WorkContractRequestData workContractRequestData = (WorkContractRequestData) data;
			return WhereReference.OTHER.equals(workContractRequestData.getWhereReference());
		}
		return true;
	}
}
