import React from 'react'
import styles from './FormItem.module.scss'
import cn from 'classnames'

interface FormItemProps {
    children: React.ReactNode
    label: string
    error?: any
    fieldName: string
    required?: boolean
    rightComponent?: React.ReactNode
    disabled?: boolean
    simple?: boolean
}

function FormItem({ children, label, error, fieldName, required, rightComponent, disabled, simple }: FormItemProps) {
    return (
        <div className={cn(styles.wrapper, { [styles.error]: error, [styles.disable]: disabled, [styles.simple]: simple })}>
            <label htmlFor={fieldName} className={cn(styles.itemLabel, { [styles.disable]: disabled })}>
                {label}
                {required && <span className={styles.star}>*</span>}
            </label>
            {children}
            {rightComponent && <div className={styles.right}>{rightComponent}</div>}
        </div>
    )
}

export default React.memo(FormItem)
