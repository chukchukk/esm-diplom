package io.demo.entity.requestdatabytype.personaldatachange;

import io.demo.entity.enums.RequestType;
import io.demo.entity.requestdatabytype.category.PersonalDataChangeCategoryRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(RequestType.RequestTypeKeyConstant.PASSPORT_CHANGE)
@NoArgsConstructor
public class PassportChangeRequestData extends PersonalDataChangeCategoryRequestData {
}
