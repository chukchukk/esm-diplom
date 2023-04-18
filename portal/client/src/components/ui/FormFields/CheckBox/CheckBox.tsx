import React from 'react'
import { Field } from '../../../../interfaces/request'
import { useFormContext, useWatch } from 'react-hook-form'
import cn from 'classnames'
import styles from './CheckBox.module.scss'

interface CheckBoxProps {
    field: Field
    disabled?: boolean
}
function CheckBox({ field, disabled }: CheckBoxProps) {
    const { register, control } = useFormContext()
    const v = useWatch({ control, name: field.option.fieldName })
    return (
        <label htmlFor={field.option.fieldName} className={cn(styles.label, { [styles.disabled]: disabled })}>
            <input
                disabled={disabled}
                type="checkbox"
                {...register(field.option.fieldName)}
                className={cn(styles.instance, {
                    [styles.checked]: v,
                    [styles.unchecked]: !v
                })}
            />
            {field.option.title}
        </label>
    )
}

export default React.memo(CheckBox)
