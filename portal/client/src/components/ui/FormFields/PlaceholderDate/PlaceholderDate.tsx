import React, { HTMLInputTypeAttribute, useEffect, useRef, useState } from 'react'
import { Field, FieldTypes } from '../../../../interfaces/request'
import { RefCallBack, useFormContext } from 'react-hook-form'
import styles from './PlaceholderDate.module.scss'
import { usePrevious } from '../../../../hooks/usePrevious'
import cn from 'classnames'

interface PlaceholderDateProps {
    field: Field
    type?: HTMLInputTypeAttribute
    max?: string
}

function PlaceholderDate({ field, max }: PlaceholderDateProps) {
    const { register, watch } = useFormContext()
    const value = watch(field.option.fieldName)
    const prevValue = usePrevious(value)
    const wasInputInit = useRef<boolean>(false)
    const inputRef = useRef<HTMLInputElement>()

    const [showPlaceholder, setShowPlaceholder] = useState<boolean>(!value)

    const handleDateFocus = () => {
        setShowPlaceholder(false)
    }

    const handleDateBlur = (e: any) => {
        const value = e.target.value
        if (!value) {
            setShowPlaceholder(true)
        }
    }

    useEffect(() => {
        if (!prevValue && value && !wasInputInit.current) {
            setShowPlaceholder(false)
            wasInputInit.current = true
        }
    }, [prevValue, value])

    const { ref: registerRef, ...inputProps } = register(field.option.fieldName, {
        required: field.isRequired,
        onBlur: handleDateBlur
    })

    const ref: RefCallBack = (instance: any) => {
        registerRef(instance)
        inputRef.current = instance
    }

    const handleClick = () => {
        inputRef.current?.focus()
    }

    return (
        <div className={styles.dateWrapper}>
            {showPlaceholder && field.option.placeholder && (
                <span className={styles.placeholder} onClick={handleClick}>
                    {field.option.placeholder}
                </span>
            )}
            <input
                type={FieldTypes.Date}
                onFocus={handleDateFocus}
                className={cn(styles.inputInstance, styles.firefoxInput)}
                {...inputProps}
                ref={ref}
                max={max}
            />
        </div>
    )
}

export default React.memo(PlaceholderDate)
