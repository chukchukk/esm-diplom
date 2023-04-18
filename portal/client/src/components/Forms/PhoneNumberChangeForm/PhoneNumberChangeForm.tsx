import React from 'react'

import styles from './PhoneNumberChangeForm.module.scss'
import { useFormContext, useFormState } from 'react-hook-form'
import FormItem from '../../ui/FormItem/FormItem'
import { commentField } from '../../../form-config/fields/common'
import Input from '../../ui/FormFields/Input/Input'
import { CommonFormProps } from '../../../interfaces/form'
import { Field } from '../../../interfaces/request'
import MaskedInput from '../../ui/FormFields/MaskedInput/MaskedInput'

const newPhoneNumber: Field = {
    isRequired: true,
    option: {
        fieldName: 'newPhoneNumber',
        title: 'Номер телефона',
        placeholder: '+7(999)999-99-99',
        fieldType: 'String',
        pattern: /^(\+7)\([0-9]{3}\)[0-9]{3}-[0-9]{2}-[0-9]{2}$/,
        mask: '+7(999)999-99-99'
    }
}
function PhoneNumberChangeForm({ selectTypeComponent, edit }: CommonFormProps) {
    const { control } = useFormContext()
    const { errors } = useFormState({ control })
    return (
        <div className={styles.formInstance}>
            {selectTypeComponent}
            <FormItem
                label={newPhoneNumber.option.title}
                fieldName={newPhoneNumber.option.fieldName}
                required={newPhoneNumber.isRequired}
                error={errors[newPhoneNumber.option.fieldName]}
            >
                <MaskedInput field={newPhoneNumber} />
            </FormItem>
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

export default React.memo(PhoneNumberChangeForm)
