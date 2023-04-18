import { Field } from '../../interfaces/request'

export const deliveryTimeFromField: Field = {
    isRequired: true,
    option: {
        fieldName: 'deliveryTimeFrom',
        title: 'Срок доставки с',
        placeholder: 'Выбери дату и время',
        fieldType: 'LocalDateTime'
    }
}
export const deliveryTimeOnField: Field = {
    isRequired: true,
    option: {
        fieldName: 'deliveryTimeOn',
        title: 'Срок доставки по',
        placeholder: 'Выбери дату и время',
        fieldType: 'LocalDateTime'
    }
}
export const deliveryTypeField: Field = {
    isRequired: true,
    option: {
        fieldName: 'deliveryType',
        title: 'Тип доставки',
        placeholder: 'Выбери тип доставки',
        fieldType: 'Select'
        // valuesKey: 'deliveryTypes'
    }
}
export const parcelTypeField: Field = {
    isRequired: true,
    option: {
        fieldName: 'parcelType',
        title: 'Вид посылки',
        placeholder: 'Выбери вид посылки',
        fieldType: 'Select'
        // valuesKey: 'parcelTypes'
    }
}
export const organizationField: Field = {
    isRequired: true,
    option: {
        fieldName: 'organization',
        title: 'Организация получатель',
        placeholder: 'Введи название организации получателя',
        fieldType: 'String'
        // valuesKey: null
    }
}
export const openingHoursField: Field = {
    isRequired: true,
    option: {
        fieldName: 'openingHours',
        title: 'Часы работы организации',
        placeholder: 'Введи часы работы организации',
        fieldType: 'String'
        // valuesKey: null
    }
}
export const contactPersonField: Field = {
    isRequired: true,
    option: {
        fieldName: 'contactPerson',
        title: 'Контактное лицо',
        placeholder: 'Введи ФИО контактного лица',
        fieldType: 'String'
        // valuesKey: null
    }
}
export const contactNumberField: Field = {
    isRequired: true,
    option: {
        fieldName: 'contactNumber',
        title: 'Контактный номер телефона',
        placeholder: 'Введи номер',
        fieldType: 'String'
        // valuesKey: null
    }
}
export const projectCodeField: Field = {
    isRequired: true,
    option: {
        fieldName: 'projectCode',
        title: 'Код проекта',
        placeholder: 'Введи код проекта',
        fieldType: 'String'
        // valuesKey: null
    }
}
// export const deliveryTimeFromField: Field ={
//     "isRequired": false,
//     "option": {
//     "fieldName": "comment",
//         "title": "Комментарий",
//         "placeholder": "Введите комментарий к заявке",
//         "fieldType": "String",
//         "valuesKey": null
// }
// },
export const expressDeliveryField: Field = {
    isRequired: true,
    option: {
        fieldName: 'expressDelivery',
        title: 'Срочная доставка',
        placeholder: '',
        fieldType: 'Checkbox'
        // valuesKey: null
    }
}
export const heavyPackageField: Field = {
    isRequired: true,
    option: {
        fieldName: 'heavyPackage',
        title: 'Посылка тяжелая',
        placeholder: '',
        fieldType: 'Checkbox'
        // valuesKey: null
    }
}
export const needPassField: Field = {
    isRequired: true,
    option: {
        fieldName: 'needPass',
        title: 'Нужен пропуск',
        placeholder: '',
        fieldType: 'Checkbox'
        // valuesKey: null
    }
}
export const requiredScanField: Field = {
    isRequired: true,
    option: {
        fieldName: 'requiredScan',
        title: 'Требуется скан подписи',
        placeholder: '',
        fieldType: 'Checkbox'
        // valuesKey: null
    }
}
export const needPowerOfAttorneyField: Field = {
    isRequired: true,
    option: {
        fieldName: 'needPowerOfAttorney',
        title: 'Нужна доверенность',
        placeholder: '',
        fieldType: 'Checkbox'
        // valuesKey: null
    }
}
export const requiredSigningField: Field = {
    isRequired: true,
    option: {
        fieldName: 'requiredSigning',
        title: 'Требуется подписание документов',
        placeholder: '',
        fieldType: 'Checkbox'
        // valuesKey: null
    }
}
