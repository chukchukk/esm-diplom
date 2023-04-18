import React from 'react'
import styles from './ReadOnlyField.module.css'
import cn from 'classnames'
import { ActionLink } from '@tesler-ui/core'
import HiddenString from '../../components/ui/HiddenString/HiddenString'

export interface ReadOnlyFieldProps {
    backgroundColor?: string
    className?: string
    onDrillDown?: (e?: any) => void
    children: React.ReactNode
    showLength?: number
}

const ReadOnlyField: React.FunctionComponent<ReadOnlyFieldProps> = props => {
    return (
        <span
            className={cn(styles.readOnlyField, { [styles.coloredField]: props.backgroundColor }, props.className)}
            style={props.backgroundColor ? { backgroundColor: props.backgroundColor } : undefined}
        >
            {props.onDrillDown ? (
                <ActionLink onClick={props.onDrillDown}>{props.children}</ActionLink>
            ) : props.showLength && typeof props.children === 'string' ? (
                <HiddenString inputString={props.children} showLength={props.showLength} />
            ) : (
                props.children
            )}
        </span>
    )
}

export default React.memo(ReadOnlyField)
