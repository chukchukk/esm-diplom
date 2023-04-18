import React, { useEffect } from 'react'
import { useFormContext, UseFormReturn, useFormState, useWatch } from 'react-hook-form'
import { commentField, newPassportFilesField } from '../../../form-config/fields/common'
import {
    actualResidenceField,
    regionField,
    districtField,
    localityField,
    streetField,
    houseField,
    flatField
} from '../../../form-config/fields/personal-data'
import FileInput from '../../ui/FormFields/FileInput/FileInput'
import Input from '../../ui/FormFields/Input/Input'
import FormItem from '../../ui/FormItem/FormItem'
import styles from './RegistrationChangeForm.module.scss'
import Select from '../../ui/FormFields/Select/Select'
import { yesNoOptions } from '../../../form-config/constants'
import { CommonFormProps } from '../../../interfaces/form'
import SingleOrSeveralFiles from '../../ui/HintContent/SingleOrSeveralFiles/SingleOrSeveralFiles'
import PopoverHint from '../../ui/PopoverHint/PopoverHint'
import WarningPanel from '../../ui/WarningPanel/WarningPanel'
import { ADDRESS_FILL_HINT } from '../../../constants/text'
import { useCustomValueSetter } from '../../../hooks/useCustomValueSetter'
import { requestByCategory } from '../../../interfaces/request'
import { RootState } from '../../../store/store'
import { useSelector } from 'react-redux'

function RegistrationChangeForm({ selectTypeComponent, edit }: CommonFormProps) {
    const formContext = useFormContext()
    const { control, resetField } = formContext
    const { errors } = useFormState({ control })
    const v = useWatch({ control, name: actualResidenceField.option.fieldName })
    useEffect(() => {
        if (v === 'true' || v === true) {
            resetField(regionField.option.fieldName)
            resetField(districtField.option.fieldName)
            resetField(localityField.option.fieldName)
            resetField(streetField.option.fieldName)
            resetField(houseField.option.fieldName)
            resetField(streetField.option.fieldName)
        }
    }, [resetField, v])

    useCommonDefaultValues(formContext, edit)

    return (
        <div className={styles.formInstance}>
            {selectTypeComponent}
            {!edit && (
                <FormItem
                    label={newPassportFilesField.option.title}
                    fieldName={newPassportFilesField.option.fieldName}
                    required={newPassportFilesField.isRequired}
                    error={errors[newPassportFilesField.option.fieldName]}
                    rightComponent={<PopoverHint content={<SingleOrSeveralFiles />} />}
                >
                    <FileInput field={newPassportFilesField} multiple />
                </FormItem>
            )}
            <FormItem
                label={actualResidenceField.option.title}
                fieldName={actualResidenceField.option.fieldName}
                required={actualResidenceField.isRequired}
                error={errors[actualResidenceField.option.fieldName]}
            >
                <Select field={actualResidenceField} options={yesNoOptions} />
            </FormItem>
            {(v === 'false' || v === false) && (
                <FormItem
                    label={regionField.option.title}
                    fieldName={regionField.option.fieldName}
                    required={regionField.isRequired}
                    error={errors[regionField.option.fieldName]}
                >
                    <Input field={regionField} />
                </FormItem>
            )}
            {(v === 'false' || v === false) && (
                <FormItem
                    label={districtField.option.title}
                    fieldName={districtField.option.fieldName}
                    required={districtField.isRequired}
                    error={errors[districtField.option.fieldName]}
                >
                    <Input field={districtField} />
                </FormItem>
            )}
            {(v === 'false' || v === false) && (
                <FormItem
                    label={localityField.option.title}
                    fieldName={localityField.option.fieldName}
                    required={localityField.isRequired}
                    error={errors[localityField.option.fieldName]}
                >
                    <Input field={localityField} />
                </FormItem>
            )}
            {(v === 'false' || v === false) && (
                <FormItem
                    label={streetField.option.title}
                    fieldName={streetField.option.fieldName}
                    required={streetField.isRequired}
                    error={errors[streetField.option.fieldName]}
                >
                    <Input field={streetField} />
                </FormItem>
            )}
            {(v === 'false' || v === false) && (
                <FormItem
                    label={houseField.option.title}
                    fieldName={houseField.option.fieldName}
                    required={houseField.isRequired}
                    error={errors[houseField.option.fieldName]}
                >
                    <Input field={houseField} />
                </FormItem>
            )}
            {(v === 'false' || v === false) && (
                <FormItem
                    label={flatField.option.title}
                    fieldName={flatField.option.fieldName}
                    required={flatField.isRequired}
                    error={errors[flatField.option.fieldName]}
                >
                    <Input field={flatField} />
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
            {(v === 'false' || v === false) && <WarningPanel title={ADDRESS_FILL_HINT} />}
        </div>
    )
}

export default RegistrationChangeForm

function useCommonDefaultValues({ getValues, setValue }: UseFormReturn, edit?: boolean) {
    const { setDefaultValue } = useCustomValueSetter({ getValues, setValue })
    const type = useSelector((store: RootState) => store.request.type)
    const create = !edit

    useEffect(() => {
        if (create && type === requestByCategory.PERSONAL_DATA_CHANGE.REGISTRATION_CHANGE) {
            setDefaultValue(actualResidenceField.option.fieldName, 'true')
        }
    }, [create, setDefaultValue, type])
}
