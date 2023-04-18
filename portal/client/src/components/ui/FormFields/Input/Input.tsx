import React, { HTMLInputTypeAttribute } from 'react'
import { Field } from '../../../../interfaces/request'
import { useFormContext } from 'react-hook-form'
import styles from './Input.module.scss'

interface InputProps {
    field: Field
    type?: HTMLInputTypeAttribute
    disabled?: boolean
}

function Input({ field, type, disabled }: InputProps) {
    const { register } = useFormContext()
    return (
        <input
            type={type}
            disabled={disabled}
            className={styles.inputInstance}
            min={field.option.min}
            {...register(field.option.fieldName, { required: field.isRequired, min: field.option.min, pattern: field.option.pattern })}
            placeholder={field.option.placeholder}
            pattern={field.option.pattern?.source}
        />
    )
}

export default React.memo(Input)
