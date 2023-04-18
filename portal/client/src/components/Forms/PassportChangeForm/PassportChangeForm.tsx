import React from 'react'
import { useFormContext, useFormState, useWatch } from 'react-hook-form'
import FormItem from '../../ui/FormItem/FormItem'
import Input from '../../ui/FormFields/Input/Input'
import FileInput from '../../ui/FormFields/FileInput/FileInput'
import { commentField, newPassportFilesField } from '../../../form-config/fields/common'
import styles from './PassportChangeForm.module.scss'
import { CommonFormProps } from '../../../interfaces/form'
import PopoverHint from '../../ui/PopoverHint/PopoverHint'
import SingleOrSeveralFiles from '../../ui/HintContent/SingleOrSeveralFiles/SingleOrSeveralFiles'
import WarningPanel from '../../ui/WarningPanel/WarningPanel'
import { NEW_DATA_CHANGE } from '../../../constants/text'
import UnapWarning from '../../ui/HintContent/UnapWarning/UnapWarning'

function PassportChangeForm({ selectTypeComponent, edit }: CommonFormProps) {
    const { control } = useFormContext()
    const { errors } = useFormState({ control })
    const v: FileList = useWatch({ control, name: newPassportFilesField.option.fieldName })
    return (
        <div className={styles.formInstance}>
            {selectTypeComponent}
            {!edit && (
                <>
                    <FormItem
                        label={newPassportFilesField.option.title}
                        fieldName={newPassportFilesField.option.fieldName}
                        required={newPassportFilesField.isRequired}
                        error={errors[newPassportFilesField.option.fieldName]}
                        rightComponent={<PopoverHint content={<SingleOrSeveralFiles />} />}
                    >
                        <FileInput field={newPassportFilesField} multiple />
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

            {v?.length > 0 && <WarningPanel title={NEW_DATA_CHANGE} content={<UnapWarning />} />}
        </div>
    )
}

export default PassportChangeForm
