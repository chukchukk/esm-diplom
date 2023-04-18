export const actualResidenceField = {
    isRequired: true,
    option: {
        fieldName: 'actualResidence',
        title: 'Фактический адрес проживания совпадает с местом регистрации',
        placeholder: 'Выбери значение',
        fieldType: 'Boolean'
    }
}

export const regionField = {
    isRequired: true,
    option: {
        fieldName: 'region',
        title: 'Регион',
        placeholder: 'Введи регион',
        fieldType: 'String'
    }
}

export const districtField = {
    isRequired: true,
    option: {
        fieldName: 'district',
        title: 'Район',
        placeholder: 'Введи район',
        fieldType: 'String'
    }
}

export const localityField = {
    isRequired: true,
    option: {
        fieldName: 'locality',
        title: 'Населенный пункт',
        placeholder: 'Введи населенный пункт',
        fieldType: 'String'
    }
}

export const streetField = {
    isRequired: true,
    option: {
        fieldName: 'street',
        title: 'Улица',
        placeholder: 'Введи улицу',
        fieldType: 'String'
    }
}

export const houseField = {
    isRequired: true,
    option: {
        fieldName: 'house',
        title: 'Дом',
        placeholder: 'Введи номер дома',
        fieldType: 'String'
    }
}

export const flatField = {
    isRequired: true,
    option: {
        fieldName: 'flat',
        title: 'Квартира',
        placeholder: 'Введи номер квартиры',
        fieldType: 'String'
    }
}
const personalDataFields = [actualResidenceField, regionField, districtField, localityField, streetField, houseField, flatField]

export default personalDataFields
