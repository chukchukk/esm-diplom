import PassportChangeForm from '../components/Forms/PassportChangeForm/PassportChangeForm'
import RegistrationChangeForm from '../components/Forms/RegistrationChangeForm/RegistrationChangeForm'
import { Category, requestByCategory, RequestCategory } from '../interfaces/request'
import { CommonFormProps, FormComponent } from '../interfaces/form'
import ChildBirthForm from '../components/Forms/ChildBirthForm/ChildBirthForm'
import PhoneNumberChangeForm from '../components/Forms/PhoneNumberChangeForm/PhoneNumberChangeForm'
import DocumentsChangeForm from '../components/Forms/DocumentsChangeForm/DocumentsChangeForm'
import DocumentForm from '../components/Forms/DocumentForm/DocumentForm'

/**
 * Temp component
 */
function UnderConstruction({ selectTypeComponent }: CommonFormProps) {
    return <div>{selectTypeComponent} 🚧🚧🚧 under construction 🚧🚧🚧</div>
}

export const availableRequestTypesOptions: {
    [category in Category]: Array<{
        key: keyof typeof requestByCategory[category]
        value: string
        component: FormComponent
    }>
} = {
    [RequestCategory.DOCUMENT]: [
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.NDFL_CERTIFICATE,
            value: 'Справка 2-НДФЛ'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.WORK_BOOK,
            value: 'Копия трудовой книжки'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.EMPLOYMENT_CERTIFICATE,
            value: 'Справка с места работы'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.MATERIAL_ASSISTANCE,
            value: 'Справка о неполучении материальной помощи'
        },
        {
            //
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.LUMP_SUM_BIRTH,
            value: 'Справка о неполучении единовременного пособия при рождении ребёнка'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.WORK_CONTRACT,
            value: 'Копия трудового договора'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.DOCUMENT.LUMP_SUM_CHILDCARE,
            value: 'Справка о неполучении пособия по уходу за ребёнком до 1.5 лет'
        }
    ],
    [RequestCategory.PERSONAL_DATA_CHANGE]: [
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <RegistrationChangeForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.PERSONAL_DATA_CHANGE.REGISTRATION_CHANGE,
            value: 'Изменение прописки'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <PassportChangeForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.PERSONAL_DATA_CHANGE.PASSPORT_CHANGE,
            value: 'Смена паспорта'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <DocumentsChangeForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.PERSONAL_DATA_CHANGE.DOCUMENTS_CHANGE,
            value: 'Смена документов в связи с изменением ФИО'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <PhoneNumberChangeForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.PERSONAL_DATA_CHANGE.PHONE_NUMBER_CHANGE,
            value: 'Изменение номера телефона'
        },
        {
            component: ({ selectTypeComponent, edit }: CommonFormProps) => (
                <ChildBirthForm selectTypeComponent={selectTypeComponent} edit={edit} />
            ),
            key: requestByCategory.PERSONAL_DATA_CHANGE.CHILD_BIRTH,
            value: 'Рождение ребёнка'
        }
    ]
}
