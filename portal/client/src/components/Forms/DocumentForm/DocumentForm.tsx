import React, { useCallback, useEffect } from 'react'
import { useFormContext, UseFormReturn, useFormState, useWatch } from 'react-hook-form'
import Input from '../../ui/FormFields/Input/Input'
import FormItem from '../../ui/FormItem/FormItem'
import styles from './DocumentForm.module.scss'
import Select from '../../ui/FormFields/Select/Select'
import { yesNoOptions } from '../../../form-config/constants'
import { CommonFormProps } from '../../../interfaces/form'

import {
    dateOfBirthChildField,
    deliveryNeedField,
    languageField,
    nameOfChildField,
    numberOfCopiesField,
    paperCopy,
    periodFromField,
    periodToField,
    showSalaryField,
    whereReferenceField,
    whereReferenceOtherField,
    travelDateFromField,
    travelDateToField,
    fullNameForeignPassportField
} from '../../../form-config/fields/document'
import { userCompanyField, deliveryAddressField, commentField } from '../../../form-config/fields/common'
import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store'
import { useGetDictionariesQuery } from '../../../store/esmService'
import { skipToken } from '@reduxjs/toolkit/query'
import { Dictionaries, DocumentTypes, inputTypeMap, requestByCategory, UserCompanySelectOption } from '../../../interfaces/request'
import PlaceholderDate from '../../ui/FormFields/PlaceholderDate/PlaceholderDate'
import SelectYear from '../../ui/FormFields/SelectYear/SelectYear'
import moment from 'moment/moment'
import { useCustomValueSetter } from '../../../hooks/useCustomValueSetter'

const paperDocumentOnlyDocumentTypes: string[] = [
    DocumentTypes.NDFL_CERTIFICATE,
    DocumentTypes.LUMP_SUM_BIRTH,
    DocumentTypes.LUMP_SUM_CHILDCARE,
    DocumentTypes.MATERIAL_ASSISTANCE
]
function DocumentForm({ selectTypeComponent, edit }: CommonFormProps) {
    const formContext = useFormContext()
    const { control } = formContext
    const { errors } = useFormState({ control })
    const category = useSelector((store: RootState) => store.request.category)
    const type = useSelector((store: RootState) => store.request.type)
    const { data: dictionaries } = useGetDictionariesQuery(category ?? skipToken)

    const paperCopyValue = useWatch({ control, name: paperCopy.option.fieldName })
    const deliveryNeedValue = useWatch({ control, name: deliveryNeedField.option.fieldName })
    const periodFromValue = useWatch({ control, name: periodFromField.option.fieldName })
    const periodFromSelected = useWatch({ control, name: periodFromField.option.fieldName, defaultValue: false })
    const whereReferenceValue = useWatch({ control, name: whereReferenceField.option.fieldName })

    useUserCompanyDefaultValue(formContext, edit, type, dictionaries)

    useCommonDefaultValues(formContext, edit, type)

    useDynamicDefaultValues(formContext, type)

    const noDelivery = deliveryNeedValue === 'false' || !deliveryNeedValue
    const noPaperCopy = paperCopyValue === 'false' || !paperCopyValue
    const noPeriodFromSelected = periodFromSelected === false || false
    const whereReferenceSelectedOther = whereReferenceValue === 'OTHER' || false
    const whereReferenceSelectedEmbassy = whereReferenceValue === 'EMBASSY' || false

    const getInnerFields = useCallback(
        (
            type: keyof typeof requestByCategory.DOCUMENT,
            periodFromSelected: boolean,
            periodFromValue: any,
            whereReferenceSelectedOther: boolean,
            whereReferenceSelectedEmbassy: boolean
        ) => {
            switch (type) {
                case requestByCategory.DOCUMENT.NDFL_CERTIFICATE: {
                    return (
                        <>
                            <FormItem
                                label={numberOfCopiesField.option.title}
                                fieldName={numberOfCopiesField.option.fieldName}
                                required={numberOfCopiesField.isRequired}
                                error={errors[numberOfCopiesField.option.fieldName]}
                            >
                                <Input
                                    field={numberOfCopiesField}
                                    type={inputTypeMap[numberOfCopiesField.option.fieldType as keyof typeof inputTypeMap]}
                                />
                            </FormItem>
                            <FormItem
                                label={'Период документа с'}
                                fieldName={periodFromField.option.fieldName}
                                required={periodFromField.isRequired}
                                error={errors[periodFromField.option.fieldName]}
                            >
                                <SelectYear field={periodFromField} placeholder={'Выбери дату'} disabled={false} />
                            </FormItem>
                            <FormItem
                                label={'Период документа по'}
                                fieldName={periodToField.option.fieldName}
                                required={periodToField.isRequired}
                                error={errors[periodToField.option.fieldName]}
                                disabled={periodFromSelected}
                            >
                                <SelectYear
                                    field={periodToField}
                                    placeholder={'Выбери дату'}
                                    disabled={periodFromSelected}
                                    lowerYear={periodFromValue}
                                />
                            </FormItem>
                        </>
                    )
                }
                case requestByCategory.DOCUMENT.EMPLOYMENT_CERTIFICATE: {
                    return (
                        <>
                            <FormItem
                                label={numberOfCopiesField.option.title}
                                fieldName={numberOfCopiesField.option.fieldName}
                                required={numberOfCopiesField.isRequired}
                                error={errors[numberOfCopiesField.option.fieldName]}
                            >
                                <Input
                                    field={numberOfCopiesField}
                                    type={inputTypeMap[numberOfCopiesField.option.fieldType as keyof typeof inputTypeMap]}
                                />
                            </FormItem>
                            <FormItem
                                label={whereReferenceField.option.title}
                                fieldName={whereReferenceField.option.fieldName}
                                required={whereReferenceField.isRequired}
                                error={errors[whereReferenceField.option.fieldName]}
                            >
                                <Select field={whereReferenceField} options={dictionaries?.['whereReferenceEC']} />
                            </FormItem>
                            {whereReferenceSelectedOther && (
                                <FormItem
                                    label={whereReferenceOtherField.option.title}
                                    fieldName={whereReferenceOtherField.option.fieldName}
                                    required={whereReferenceOtherField.isRequired}
                                    error={errors[whereReferenceOtherField.option.fieldName]}
                                >
                                    <Input field={whereReferenceOtherField} type={whereReferenceOtherField.option.fieldType} />
                                </FormItem>
                            )}
                            {whereReferenceSelectedEmbassy && (
                                <FormItem
                                    label={travelDateFromField.option.title}
                                    fieldName={travelDateFromField.option.fieldName}
                                    required={travelDateFromField.isRequired}
                                    error={errors[travelDateFromField.option.fieldName]}
                                >
                                    <PlaceholderDate field={travelDateFromField} type={travelDateFromField.option.fieldType} />
                                </FormItem>
                            )}
                            {whereReferenceSelectedEmbassy && (
                                <FormItem
                                    label={travelDateToField.option.title}
                                    fieldName={travelDateToField.option.fieldName}
                                    required={travelDateToField.isRequired}
                                    error={errors[travelDateToField.option.fieldName]}
                                >
                                    <PlaceholderDate field={travelDateToField} type={travelDateToField.option.fieldType} />
                                </FormItem>
                            )}
                            {whereReferenceSelectedEmbassy && (
                                <FormItem
                                    label={fullNameForeignPassportField.option.title}
                                    fieldName={fullNameForeignPassportField.option.fieldName}
                                    required={fullNameForeignPassportField.isRequired}
                                    error={errors[fullNameForeignPassportField.option.fieldName]}
                                >
                                    <Input field={fullNameForeignPassportField} type={fullNameForeignPassportField.option.fieldType} />
                                </FormItem>
                            )}
                            <FormItem
                                label={showSalaryField.option.title}
                                fieldName={showSalaryField.option.fieldName}
                                required={showSalaryField.isRequired}
                                error={errors[showSalaryField.option.fieldName]}
                            >
                                <Select field={showSalaryField} options={yesNoOptions} />
                            </FormItem>
                            <FormItem
                                label={languageField.option.title}
                                fieldName={languageField.option.fieldName}
                                required={languageField.isRequired}
                                error={errors[languageField.option.fieldName]}
                            >
                                <Select field={languageField} options={dictionaries?.[languageField.option.fieldName]} />
                            </FormItem>
                        </>
                    )
                }
                case requestByCategory.DOCUMENT.WORK_CONTRACT: {
                    return (
                        <>
                            <FormItem
                                label={numberOfCopiesField.option.title}
                                fieldName={numberOfCopiesField.option.fieldName}
                                required={numberOfCopiesField.isRequired}
                                error={errors[numberOfCopiesField.option.fieldName]}
                            >
                                <Input
                                    field={numberOfCopiesField}
                                    type={inputTypeMap[numberOfCopiesField.option.fieldType as keyof typeof inputTypeMap]}
                                />
                            </FormItem>
                            <FormItem
                                label={whereReferenceField.option.title}
                                fieldName={whereReferenceField.option.fieldName}
                                required={whereReferenceField.isRequired}
                                error={errors[whereReferenceField.option.fieldName]}
                            >
                                <Select field={whereReferenceField} options={dictionaries?.['whereReferenceWC']} />
                            </FormItem>
                            {whereReferenceSelectedOther && (
                                <FormItem
                                    label={whereReferenceOtherField.option.title}
                                    fieldName={whereReferenceOtherField.option.fieldName}
                                    required={whereReferenceOtherField.isRequired}
                                    error={errors[whereReferenceOtherField.option.fieldName]}
                                >
                                    <Input field={whereReferenceOtherField} type={whereReferenceOtherField.option.fieldType} />
                                </FormItem>
                            )}
                        </>
                    )
                }
                case requestByCategory.DOCUMENT.WORK_BOOK: {
                    return (
                        <>
                            <FormItem
                                label={numberOfCopiesField.option.title}
                                fieldName={numberOfCopiesField.option.fieldName}
                                required={numberOfCopiesField.isRequired}
                                error={errors[numberOfCopiesField.option.fieldName]}
                            >
                                <Input
                                    field={numberOfCopiesField}
                                    type={inputTypeMap[numberOfCopiesField.option.fieldType as keyof typeof inputTypeMap]}
                                />
                            </FormItem>
                            <FormItem
                                label={whereReferenceField.option.title}
                                fieldName={whereReferenceField.option.fieldName}
                                required={whereReferenceField.isRequired}
                                error={errors[whereReferenceField.option.fieldName]}
                            >
                                <Select field={whereReferenceField} options={dictionaries?.['whereReferenceWB']} />
                            </FormItem>
                            {whereReferenceSelectedOther && (
                                <FormItem
                                    label={whereReferenceOtherField.option.title}
                                    fieldName={whereReferenceOtherField.option.fieldName}
                                    required={whereReferenceOtherField.isRequired}
                                    error={errors[whereReferenceOtherField.option.fieldName]}
                                >
                                    <Input field={whereReferenceOtherField} type={whereReferenceOtherField.option.fieldType} />
                                </FormItem>
                            )}
                        </>
                    )
                }
                case requestByCategory.DOCUMENT.LUMP_SUM_BIRTH:
                case requestByCategory.DOCUMENT.MATERIAL_ASSISTANCE:
                case requestByCategory.DOCUMENT.LUMP_SUM_CHILDCARE:
                    return (
                        <>
                            <FormItem
                                label={nameOfChildField.option.title}
                                fieldName={nameOfChildField.option.fieldName}
                                required={nameOfChildField.isRequired}
                                error={errors[nameOfChildField.option.fieldName]}
                            >
                                <Input field={nameOfChildField} type={nameOfChildField.option.fieldType} />
                            </FormItem>
                            <FormItem
                                label={dateOfBirthChildField.option.title}
                                fieldName={dateOfBirthChildField.option.fieldName}
                                required={dateOfBirthChildField.isRequired}
                                error={errors[dateOfBirthChildField.option.fieldName]}
                            >
                                <PlaceholderDate
                                    field={dateOfBirthChildField}
                                    type={dateOfBirthChildField.option.fieldType}
                                    max={new Date().toISOString().split('T')[0]}
                                />
                            </FormItem>
                        </>
                    )
            }
        },
        [errors, dictionaries]
    )

    return (
        <div className={styles.formInstance}>
            {selectTypeComponent}
            <FormItem
                label={userCompanyField.option.title}
                fieldName={userCompanyField.option.fieldName}
                required={userCompanyField.isRequired}
                error={errors[userCompanyField.option.fieldName]}
            >
                <Select field={userCompanyField} options={dictionaries?.[userCompanyField.option.fieldName]} />
            </FormItem>
            {getInnerFields(
                type as keyof typeof requestByCategory.DOCUMENT,
                noPeriodFromSelected,
                periodFromValue,
                whereReferenceSelectedOther,
                whereReferenceSelectedEmbassy
            )}

            <FormItem
                label={paperCopy.option.title}
                fieldName={paperCopy.option.fieldName}
                required={paperCopy.isRequired}
                error={errors[paperCopy.option.fieldName]}
                disabled={!!(type && paperDocumentOnlyDocumentTypes.includes(type))}
            >
                <Select field={paperCopy} options={yesNoOptions} disabled={!!(type && paperDocumentOnlyDocumentTypes.includes(type))} />
            </FormItem>
            {!noPaperCopy && (
                <FormItem
                    label={deliveryNeedField.option.title}
                    fieldName={deliveryNeedField.option.fieldName}
                    required={deliveryNeedField.isRequired}
                    error={errors[deliveryNeedField.option.fieldName]}
                >
                    <Select field={deliveryNeedField} options={yesNoOptions} />
                </FormItem>
            )}
            {!noDelivery && (
                <FormItem
                    label={deliveryAddressField.option.title}
                    fieldName={deliveryAddressField.option.fieldName}
                    required={deliveryAddressField.isRequired}
                    error={errors[deliveryAddressField.option.fieldName]}
                >
                    <Input field={deliveryAddressField} type={deliveryAddressField.option.fieldType} />
                </FormItem>
            )}
            {!edit && (
                <FormItem
                    label={commentField.option.title}
                    fieldName={commentField.option.fieldName}
                    required={commentField.isRequired}
                    error={errors[commentField.option.fieldName]}
                >
                    <Input field={commentField} />
                </FormItem>
            )}
        </div>
    )
}

export default DocumentForm

const defaultUserCompanyOnlyForDocumentTypes: string[] = [
    DocumentTypes.NDFL_CERTIFICATE,
    DocumentTypes.MATERIAL_ASSISTANCE,
    DocumentTypes.LUMP_SUM_BIRTH,
    DocumentTypes.LUMP_SUM_CHILDCARE,
    DocumentTypes.WORK_BOOK,
    DocumentTypes.WORK_CONTRACT,
    DocumentTypes.EMPLOYMENT_CERTIFICATE
]

function useUserCompanyDefaultValue(formContext: UseFormReturn, edit?: boolean, type: string = '', dictionaries?: Dictionaries) {
    const { setDefaultValue } = useCustomValueSetter(formContext)
    const create = !edit

    useEffect(() => {
        const mainUserCompany = dictionaries?.[userCompanyField.option.fieldName]?.find((item: UserCompanySelectOption) => item.main)

        if (create && mainUserCompany && defaultUserCompanyOnlyForDocumentTypes.includes(type)) {
            setDefaultValue(userCompanyField.option.fieldName, mainUserCompany.key as string)
        }
    }, [create, dictionaries, setDefaultValue, type])
}

function useCommonDefaultValues({ getValues, setValue }: UseFormReturn, edit?: boolean, type: string = '') {
    const { setDefaultValue } = useCustomValueSetter({ getValues, setValue })
    const create = !edit

    useEffect(() => {
        if (type && paperDocumentOnlyDocumentTypes.includes(type)) {
            setValue(paperCopy.option.fieldName, 'true')
        }

        if (create && type === requestByCategory.DOCUMENT.NDFL_CERTIFICATE) {
            setDefaultValue(numberOfCopiesField.option.fieldName, '2')

            const currentYear = moment().format('YYYY')
            const periodFrom = (parseInt(currentYear) - 1).toString()

            setDefaultValue(periodFromField.option.fieldName, periodFrom)
            setDefaultValue(periodToField.option.fieldName, currentYear)

            setDefaultValue(deliveryNeedField.option.fieldName, 'false')
        }

        if (create && type === requestByCategory.DOCUMENT.MATERIAL_ASSISTANCE) {
            setDefaultValue(deliveryNeedField.option.fieldName, 'false')
        }

        if (create && type === requestByCategory.DOCUMENT.LUMP_SUM_BIRTH) {
            setDefaultValue(deliveryNeedField.option.fieldName, 'false')
        }

        if (create && type === requestByCategory.DOCUMENT.LUMP_SUM_CHILDCARE) {
            setDefaultValue(deliveryNeedField.option.fieldName, 'false')
        }

        if (create && type === requestByCategory.DOCUMENT.WORK_BOOK) {
            setDefaultValue(numberOfCopiesField.option.fieldName, '1')
            setDefaultValue(paperCopy.option.fieldName, 'false')
        }

        if (create && type === requestByCategory.DOCUMENT.WORK_CONTRACT) {
            setDefaultValue(numberOfCopiesField.option.fieldName, '1')
            setDefaultValue(paperCopy.option.fieldName, 'false')
        }

        if (create && type === requestByCategory.DOCUMENT.EMPLOYMENT_CERTIFICATE) {
            setDefaultValue(numberOfCopiesField.option.fieldName, '1')
            setDefaultValue(showSalaryField.option.fieldName, 'true')
            setDefaultValue(languageField.option.fieldName, 'RUS')
            setDefaultValue(paperCopy.option.fieldName, 'false')
            setDefaultValue(whereReferenceField.option.fieldName, 'BANK')
        }
    }, [create, setDefaultValue, setValue, type])
}

const dynamicDeliveryNeedOnlyForDocumentTypes: string[] = [
    DocumentTypes.WORK_BOOK,
    DocumentTypes.WORK_CONTRACT,
    DocumentTypes.EMPLOYMENT_CERTIFICATE
]

function useDynamicDefaultValues({ control, getValues, setValue }: UseFormReturn, type: string = '') {
    const paperCopyValue = useWatch({ control, name: paperCopy.option.fieldName })
    const { setDefaultValue, resetValue } = useCustomValueSetter({ setValue, getValues })

    useEffect(() => {
        if (dynamicDeliveryNeedOnlyForDocumentTypes.includes(type)) {
            const yesPaperCopy = paperCopyValue === 'true'
            const noPaperCopy = !yesPaperCopy

            yesPaperCopy && setDefaultValue(deliveryNeedField.option.fieldName, 'false')
            resetValue(deliveryNeedField.option.fieldName, noPaperCopy)
            resetValue(deliveryAddressField.option.fieldName, noPaperCopy)
        }
    }, [paperCopyValue, resetValue, setDefaultValue, type])
}
