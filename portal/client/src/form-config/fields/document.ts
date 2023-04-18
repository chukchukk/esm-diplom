import { Field } from '../../interfaces/request'

export const nameOfChildField: Field = {
    isRequired: true,
    option: {
        fieldName: 'nameOfChild',
        title: 'ФИО ребёнка',
        fieldType: 'String',
        placeholder: 'Введи ФИО ребёнка'
    }
}
export const dateOfBirthChildField: Field = {
    isRequired: true,
    option: {
        fieldName: 'dateOfBirthChild',
        title: 'Дата рождения ребёнка',
        fieldType: 'Date',
        placeholder: 'Выбери дату'
    }
}
export const deliveryNeedField: Field = {
    isRequired: true,
    option: {
        fieldName: 'deliveryNeed',
        title: 'Требуется доставка',
        fieldType: 'Boolean',
        placeholder: 'Выбери значение'
    }
}
export const paperCopy: Field = {
    isRequired: true,
    option: {
        fieldName: 'paperCopy',
        title: 'Нужна бумажная версия документа',
        fieldType: 'Boolean',
        placeholder: 'Выбери значение'
    }
}
export const numberOfCopiesField: Field = {
    isRequired: true,
    option: {
        fieldName: 'numberOfCopies',
        title: 'Количество экземпляров',
        fieldType: 'Integer',
        min: 1,
        placeholder: 'Введи количество'
    }
}
export const whereReferenceField: Field = {
    isRequired: true,
    option: {
        fieldName: 'whereReference',
        title: 'Место предоставления',
        fieldType: 'Select',
        placeholder: 'Выбери значение'
    }
}
export const whereReferenceOtherField: Field = {
    isRequired: true,
    option: {
        fieldName: 'whereReferenceOther',
        title: 'Иное место предоставления',
        fieldType: 'String',
        placeholder: 'Укажи, куда требуется документ'
    }
}
export const travelDateFromField: Field = {
    isRequired: true,
    option: {
        fieldName: 'travelDateFrom',
        title: 'Дата начала поездки',
        fieldType: 'Date',
        placeholder: 'Выбери дату'
    }
}
export const travelDateToField: Field = {
    isRequired: true,
    option: {
        fieldName: 'travelDateTo',
        title: 'Дата окончания поездки',
        fieldType: 'Date',
        placeholder: 'Выбери дату'
    }
}
export const fullNameForeignPassportField: Field = {
    isRequired: true,
    option: {
        fieldName: 'fullNameForeignPassport',
        title: 'Фамилия и имя из заграничного паспорта',
        fieldType: 'String',
        placeholder: 'Введи информацию на латинице',
        pattern: /([A-Za-z\s-]*)/
    }
}
export const showSalaryField: Field = {
    isRequired: true,
    option: {
        fieldName: 'showSalary',
        title: 'Указывать зарплату',
        fieldType: 'Select',
        placeholder: 'Выбери значение'
    }
}
export const languageField: Field = {
    isRequired: true,
    option: {
        fieldName: 'language',
        title: 'Язык документа',
        fieldType: 'String',
        placeholder: 'Выбери язык'
    }
}
export const periodFromField: Field = {
    isRequired: true,
    option: {
        fieldName: 'periodFrom',
        title: 'Период с',
        fieldType: 'Integer',
        placeholder: 'Введи год'
    }
}
export const periodToField: Field = {
    isRequired: true,
    option: {
        fieldName: 'periodTo',
        title: 'Период по',
        fieldType: 'Integer',
        placeholder: 'Введи год'
    }
}
