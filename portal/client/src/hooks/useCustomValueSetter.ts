import { UseFormReturn } from 'react-hook-form'
import { useCallback } from 'react'
import { FieldTypes } from '../interfaces/request'

export function useCustomValueSetter({ getValues, setValue }: Pick<UseFormReturn, 'getValues' | 'setValue'>) {
    const setDefaultValue = useCallback(
        (name: string, value: string | null) => {
            const currentValue = getValues(name)
            let valueIsEmpty = !currentValue && !['string'].includes(typeof currentValue)

            if (FieldTypes.Integer) {
                valueIsEmpty = !currentValue && !['number'].includes(typeof currentValue)
            }

            valueIsEmpty && setValue(name, value)
        },
        [getValues, setValue]
    )

    const resetValue = useCallback(
        (name: string, resetCondition: boolean = true) => {
            if (resetCondition) {
                setValue(name, null)
            }
        },
        [setValue]
    )

    return {
        setDefaultValue,
        resetValue
    }
}
