import { Field } from '../../interfaces/request'

export const newPassportFilesField: Field = {
    isRequired: true,
    option: {
        fieldName: 'newPassportFiles',
        title: 'Паспорт',
        placeholder: 'Прикрепи все заполненные страницы паспорта',
        fieldType: 'File'
    }
}
export const commentField: Field = {
    isRequired: false,
    option: {
        fieldName: 'comment',
        title: 'Комментарий к заявке',
        placeholder: 'Введи текст',
        fieldType: 'String'
    }
}
export const userCompanyField: Field = {
    isRequired: true,
    option: {
        fieldName: 'userCompany',
        title: 'Юридическое лицо',
        placeholder: 'Выбери компанию',
        fieldType: 'Select'
        // valuesKey: 'companies'
    }
}
export const deliveryAddressField: Field = {
    isRequired: true,
    option: {
        fieldName: 'deliveryAddress',
        title: 'Адрес доставки',
        placeholder: 'Введи адрес доставки',
        fieldType: 'String'
        // valuesKey: null
    }
}

const commonFields = [newPassportFilesField, commentField]

export default commonFields
