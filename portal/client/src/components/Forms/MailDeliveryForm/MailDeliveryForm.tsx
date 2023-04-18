import React from 'react'
import { useFormContext, useFormState } from 'react-hook-form'
import {
    contactNumberField,
    contactPersonField,
    deliveryTimeFromField,
    deliveryTimeOnField,
    deliveryTypeField,
    expressDeliveryField,
    heavyPackageField,
    needPassField,
    needPowerOfAttorneyField,
    openingHoursField,
    organizationField,
    parcelTypeField,
    projectCodeField,
    requiredScanField,
    requiredSigningField
} from '../../../form-config/fields/administrative-support'
import { userCompanyField, deliveryAddressField, commentField } from '../../../form-config/fields/common'
import Input from '../../ui/FormFields/Input/Input'
import FormItem from '../../ui/FormItem/FormItem'
import CheckBox from '../../ui/FormFields/CheckBox/CheckBox'
import { useGetDictionariesQuery } from '../../../store/esmService'
import { useSelector } from 'react-redux'
import { RootState } from '../../../store/store'
import Select from '../../ui/FormFields/Select/Select'
import { FieldTypes } from '../../../interfaces/request'
import { skipToken } from '@reduxjs/toolkit/query'
import styles from './MailDeliveryForm.module.scss'
import { CommonFormProps } from '../../../interfaces/form'

const deliveryFeaturesFields = [
    expressDeliveryField,
    heavyPackageField,
    needPassField,
    requiredScanField,
    needPowerOfAttorneyField,
    requiredSigningField
]

const restFields = [
    deliveryTypeField,
    parcelTypeField,
    userCompanyField,
    organizationField,
    deliveryAddressField,
    openingHoursField,
    contactPersonField,
    contactNumberField,
    projectCodeField,
    commentField
]

function MailDeliveryForm({ selectTypeComponent, edit }: CommonFormProps) {
    const { control } = useFormContext()
    const { errors } = useFormState({ control })
    const category = useSelector((store: RootState) => store.request.category)
    const { data: dictionaries } = useGetDictionariesQuery(category ?? skipToken)

    return (
        <div className={styles.formInstance}>
            <div className={styles.leftColumn}>
                {selectTypeComponent}
                {restFields?.map(i => {
                    if (i.option.fieldName === commentField.option.fieldName && edit) {
                        return null
                    }
                    let fieldComponent = null
                    switch (i.option.fieldType) {
                        case FieldTypes.Select: {
                            fieldComponent = <Select key={i.option.fieldName} field={i} options={dictionaries?.[i.option.fieldName]} />
                            break
                        }
                        default: {
                            fieldComponent = <Input field={i} />
                        }
                    }
                    return (
                        <FormItem
                            label={i.option.title}
                            key={i.option.fieldName}
                            fieldName={i.option.fieldName}
                            required={i.isRequired}
                            error={errors[i.option.fieldName]}
                        >
                            {fieldComponent}
                        </FormItem>
                    )
                })}
            </div>
            <div className={styles.rightColumn}>
                <FormItem
                    key={deliveryTimeFromField.option.fieldName}
                    fieldName={deliveryTimeFromField.option.fieldName}
                    label={deliveryTimeFromField.option.title}
                    error={errors[deliveryTimeFromField.option.fieldName]}
                    required={deliveryTimeFromField.isRequired}
                >
                    <Input field={deliveryTimeFromField} type="datetime-local" />
                </FormItem>
                <FormItem
                    key={deliveryTimeOnField.option.fieldName}
                    fieldName={deliveryTimeOnField.option.fieldName}
                    label={deliveryTimeOnField.option.title}
                    error={errors[deliveryTimeOnField.option.fieldName]}
                    required={deliveryTimeOnField.isRequired}
                >
                    <Input field={deliveryTimeOnField} type="datetime-local" />
                </FormItem>
                <fieldset className={styles.checkboxes}>
                    <legend className={styles.label}>Особенности доставки</legend>
                    {deliveryFeaturesFields?.map(i => {
                        return <CheckBox field={i} key={i.option.fieldName} />
                    })}
                </fieldset>
            </div>
        </div>
    )
}

export default MailDeliveryForm
