import React from 'react'

import styles from './ChildBirthForm.module.scss'
import FormItem from '../../ui/FormItem/FormItem'
import { CommonFormProps } from '../../../interfaces/form'
import { commentField } from '../../../form-config/fields/common'
import Input from '../../ui/FormFields/Input/Input'
import { useFormContext, useFormState } from 'react-hook-form'
import { Field } from '../../../interfaces/request'
import FileInput from '../../ui/FormFields/FileInput/FileInput'

const childBirthCertificate: Field = {
    isRequired: true,
    option: {
        fieldName: 'childBirthCertificate',
        title: 'Свидетельство о рождении',
        placeholder: 'Прикрепи свидетельство о рождении ребёнка',
        fieldType: 'File'
    }
}

function ChildBirthForm({ selectTypeComponent, edit }: CommonFormProps) {
    const { control } = useFormContext()
    const { errors } = useFormState({ control })
    return (
        <div className={styles.formInstance}>
            {selectTypeComponent}
            {!edit && (
                <>
                    <FormItem
                        label={childBirthCertificate.option.title}
                        fieldName={childBirthCertificate.option.fieldName}
                        required={childBirthCertificate.isRequired}
                        error={errors[childBirthCertificate.option.fieldName]}
                    >
                        <FileInput field={childBirthCertificate} />
                    </FormItem>
                    <FormItem
                        label={commentField.option.title}
                        fieldName={commentField.option.fieldName}
                        required={commentField.isRequired}
                        error={errors[commentField.option.fieldName]}
                    >
                        <Input field={commentField} />
                    </FormItem>
                </>
            )}
        </div>
    )
}

export default React.memo(ChildBirthForm)
