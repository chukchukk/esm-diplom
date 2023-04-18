import React from 'react'
import { Field, SelectOption } from '../../../../interfaces/request'
import { useFormContext, Controller } from 'react-hook-form'
import CustomSelect from '../../CustomSelect/CustomSelect'

interface SelectProps {
    field: Field
    options?: SelectOption[]
    disabled?: boolean
}

function Select({ field, options, disabled }: SelectProps) {
    const { control } = useFormContext()

    return (
        <Controller
            control={control}
            name={field.option.fieldName}
            rules={{ required: field.isRequired }}
            render={({ field: fieldProps }) => {
                return (
                    <CustomSelect
                        {...fieldProps}
                        placeholder={field.option.placeholder}
                        name={field.option.fieldName}
                        options={options}
                        disabled={disabled}
                        isClearable={true}
                    />
                )
            }}
        />
    )
}

export default React.memo(Select)
