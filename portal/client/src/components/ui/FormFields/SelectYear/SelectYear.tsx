import { Field, SelectOption } from '../../../../interfaces/request'
import React from 'react'
import { Controller, useFormContext } from 'react-hook-form'
import CustomSelect from '../../CustomSelect/CustomSelect'

interface SelectYearProps {
    field: Field
    placeholder: any
    disabled: any
    lowerYear?: any
}

function SelectYear({ field, placeholder, disabled, lowerYear }: SelectYearProps) {
    const { control } = useFormContext()
    const year = new Date().getFullYear()
    const yearsSize = lowerYear ? year - lowerYear + 1 : 100
    const years: SelectOption[] = Array.from(new Array(yearsSize), (val, index) => String(year - index)).map(item => ({
        key: item,
        value: item
    }))

    return (
        <Controller
            control={control}
            name={field.option.fieldName}
            rules={{ required: field.isRequired }}
            render={({ field: fieldProps }) => {
                return (
                    <CustomSelect
                        {...fieldProps}
                        placeholder={placeholder ?? field.option.placeholder}
                        name={field.option.fieldName}
                        options={years}
                        disabled={disabled}
                        isClearable={true}
                    />
                )
            }}
        />
    )
}

export default React.memo(SelectYear)
