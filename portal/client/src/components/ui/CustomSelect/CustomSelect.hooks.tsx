import { useCallback, useMemo } from 'react'
import { NamedProps } from 'react-select/src/Select'
import { CustomSelectProps } from './CustomSelect'

export function useNormalizedOptions(options: CustomSelectProps['options']): ReadonlyArray<{ label: string; value: string }> | undefined {
    return useMemo(() => {
        return options?.map(item => ({ label: item.value, value: item.key?.toString() }))
    }, [options])
}

export function useSimpleValueForSelect(
    value: CustomSelectProps['value'],
    options: ReturnType<typeof useNormalizedOptions>,
    onChange: CustomSelectProps['onChange']
) {
    const handleChange: NamedProps['onChange'] | undefined = useCallback(
        (value: { label: string; value: string } | null) => {
            onChange?.(value?.value ?? '')
        },
        [onChange]
    )

    const currentValue = options?.find(item => item.value === value?.toString())

    return {
        currentValue,
        handleChange
    }
}
