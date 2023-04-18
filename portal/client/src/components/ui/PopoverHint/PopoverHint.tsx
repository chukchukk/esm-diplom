import React from 'react'
import Popover from '../Popover/Popover'
import styles from './PopoverHint.module.scss'
import cn from 'classnames'

interface PopoverHintProps {
    content: React.ReactNode
    disabled?: boolean
}

function PopoverHint({ content, disabled }: PopoverHintProps) {
    return (
        <Popover content={content} disabled={disabled}>
            <div className={cn(styles.root, { [styles.disabled]: disabled })} />
        </Popover>
    )
}

export default React.memo(PopoverHint)
