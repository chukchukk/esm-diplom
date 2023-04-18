export interface SelectOption {
    key: string | number
    value: string
}

export enum FieldTypes {
    File = 'File',
    String = 'String',
    Select = 'Select',
    Integer = 'Integer',
    LocalDateTime = 'LocalDateTime',
    Checkbox = 'Checkbox',
    Date = 'Date',
    Boolean = 'Boolean'
}

export enum UIFieldNames {
    comment = 'comment'
}

interface typeOption {
    fieldName: string
    fieldType: FieldTypes | string
    title: string
    placeholder: string
    valuesKey?: keyof ArrayLikeInfos
    min?: number
    mask?: string | Array<string | RegExp>
    pattern?: RegExp
}

export const requestTypeOptions = {
    EMPLOYMENT_CERTIFICATE: 'EMPLOYMENT_CERTIFICATE',
    LUMP_SUM_BIRTH: 'LUMP_SUM_BIRTH',
    PERSONAL_DATA_CHANGE: 'PERSONAL_DATA_CHANGE',
    LUMP_SUM_CHILDCARE: 'LUMP_SUM_CHILDCARE',
    MATERIAL_ASSISTANCE: 'MATERIAL_ASSISTANCE',
    NDFL_CERTIFICATE: 'NDFL_CERTIFICATE',
    WORK_BOOK: 'WORK_BOOK'
} as const

export type RequestType = string

export const RequestCategory = {
    DOCUMENT: 'DOCUMENT',
    PERSONAL_DATA_CHANGE: 'PERSONAL_DATA_CHANGE'
} as const

export const DocumentTypes = {
    EMPLOYMENT_CERTIFICATE: 'EMPLOYMENT_CERTIFICATE',
    NDFL_CERTIFICATE: 'NDFL_CERTIFICATE',
    WORK_BOOK: 'WORK_BOOK',
    WORK_CONTRACT: 'WORK_CONTRACT',
    LUMP_SUM_BIRTH: 'LUMP_SUM_BIRTH',
    LUMP_SUM_CHILDCARE: 'LUMP_SUM_CHILDCARE',
    MATERIAL_ASSISTANCE: 'MATERIAL_ASSISTANCE'
} as const

export const personalDataChangeTypes = {
    REGISTRATION_CHANGE: 'REGISTRATION_CHANGE',
    PASSPORT_CHANGE: 'PASSPORT_CHANGE',
    DOCUMENTS_CHANGE: 'DOCUMENTS_CHANGE',
    PHONE_NUMBER_CHANGE: 'PHONE_NUMBER_CHANGE',
    CHILD_BIRTH: 'CHILD_BIRTH'
} as const

export type RequestCategoryType = keyof typeof RequestCategory

export type AnyRequestType = keyof typeof requestTypeOptions | keyof typeof DocumentTypes | keyof typeof personalDataChangeTypes

interface ICompany {
    legalEntityId: number
    title: string
}

export interface Field {
    isRequired: boolean
    option: typeOption
}

export interface FileInfo {
    key: string
    value: string
}

export interface ApplicationsFilesDictionaries {
    lastNameChangeApplications?: FileInfo[]
    snilsChangeApplications?: FileInfo[]
}

export interface ArrayLikeInfos extends ApplicationsFilesDictionaries {
    companies: ICompany[]
    languages: SelectOption[]
    deliveryTypes?: SelectOption[]
    parcelTypes?: SelectOption[]
}

export interface OptionalInfo extends ArrayLikeInfos {
    requestTypes: Record<keyof typeof requestTypeOptions, string>
}

export interface IGetStartResponse {
    category: keyof typeof RequestCategory
    optionalInfo: OptionalInfo
    requestTypeOptions: Record<string, Array<Field>>
}

export interface RequestSlice {
    category?: RequestCategoryType
    type?: RequestType
    creationLock?: boolean
}

export type UserCompanySelectOption = SelectOption & { main?: boolean }

export type Dictionaries = Record<string, SelectOption[] | UserCompanySelectOption[]>

export class RequestByCategory {
    DOCUMENT = DocumentTypes
    PERSONAL_DATA_CHANGE = personalDataChangeTypes
}

export const requestByCategory = new RequestByCategory()

export type Category = keyof typeof requestByCategory

export const inputTypeMap = {
    String: 'text',
    Boolean: 'boolean',
    Date: 'date',
    Integer: 'number'
}
