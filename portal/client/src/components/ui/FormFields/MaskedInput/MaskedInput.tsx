import React from 'react'
import { useFormContext, Controller } from 'react-hook-form'
import InputMask from 'react-input-mask'

import styles from './MaskedInput.module.scss'
import { Field } from '../../../../interfaces/request'

interface MaskedInputProps {
    field: Field
}

function MaskedInput({ field }: MaskedInputProps) {
    const { control } = useFormContext()
    if (!field.option.mask) {
        console.error('Необходимо задать маску ввода')
        return null
    }
    return (
        <Controller
            control={control}
            name={field.option.fieldName}
            rules={{ required: field.isRequired, pattern: field.option.pattern }}
            render={({ field: { onChange, onBlur, value, name, ref } }) => (
                <InputMask
                    className={styles.inputInstance}
                    mask={field.option.mask!}
                    inputRef={ref}
                    onChange={onChange}
                    value={value}
                    placeholder={field.option.placeholder}
                />
            )}
        />
    )
}

export default React.memo(MaskedInput)
