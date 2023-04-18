import React from 'react'
import Select from 'react-select'
import { NamedProps } from 'react-select/src/Select'
import DropdownIndicator from './DropdownIndicator'
import EmptyComponent from './EmptyComponent'
import { selectStyles, selectTheme } from './CustomSelect.style'
import styles from './CustomSelect.module.scss'
import { SelectOption } from '../../../interfaces/request'
import { useNormalizedOptions, useSimpleValueForSelect } from './CustomSelect.hooks'

export interface CustomSelectProps extends Omit<NamedProps, 'isDisabled' | 'options' | 'onChange' | 'value'> {
    disabled?: boolean
    options?: SelectOption[]
    onChange?: (value: string) => void
    value?: string
}

function CustomSelect({ disabled, options, onChange, value, ...restProps }: CustomSelectProps) {
    const normalizedOptions = useNormalizedOptions(options)
    const { currentValue, handleChange } = useSimpleValueForSelect(value, normalizedOptions, onChange)

    return (
        <Select
            className={styles.root}
            {...restProps}
            components={{
                ClearIndicator: EmptyComponent,
                IndicatorSeparator: EmptyComponent,
                DropdownIndicator: DropdownIndicator
            }}
            options={normalizedOptions}
            theme={selectTheme}
            styles={selectStyles}
            isDisabled={disabled}
            onChange={handleChange}
            value={currentValue}
        />
    )
}

export default React.memo(CustomSelect)
