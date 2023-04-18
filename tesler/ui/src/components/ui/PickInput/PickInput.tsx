import React from 'react'
import { Input, Icon } from 'antd'
import { useTranslation } from '@tesler-ui/core'
import styles from './PickInput.module.css'

export interface PickInputProps {
    disabled?: boolean
    value?: string
    onClick?: () => void
    onClear?: () => void
    className?: string
    placeholder?: string
    loading?: boolean
}

/**
 *
 * @param props
 * @category Components
 */
const PickInput: React.FunctionComponent<PickInputProps> = props => {
    const { disabled, value, onClick, onClear, className, placeholder, loading } = props
    const handleClick = React.useCallback(() => {
        if (!disabled && onClick) {
            onClick()
        }
    }, [disabled, onClick])

    const { t } = useTranslation()

    const clearButton = onClear && !disabled && value ? <Icon type="close-circle" onClick={onClear} /> : null

    return (
        <Input
            disabled={disabled}
            readOnly
            placeholder={placeholder ?? t('Select value')}
            value={value || ''}
            suffix={clearButton}
            className={className}
            addonAfter={
                loading ? (
                    <Icon type="loading" spin />
                ) : (
                    <Icon
                        className={disabled ? styles.disabledButton : undefined}
                        type="paper-clip"
                        onClick={!disabled ? handleClick : undefined}
                    />
                )
            }
        />
    )
}

/**
 * @category Components
 */
const MemoizedPickInput = React.memo(PickInput)

export default MemoizedPickInput
