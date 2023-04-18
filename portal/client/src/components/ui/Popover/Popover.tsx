import React from 'react'
import styles from './Popover.module.scss'
import cn from 'classnames'

interface PopoverProps {
    children: React.ReactNode
    content: React.ReactNode
    disabled?: boolean
}

function Popover({ children, content, disabled }: PopoverProps) {
    return (
        <div className={cn(styles.wrapper, { [styles.disabled]: disabled })}>
            {children}
            <div className={styles.content}>{content}</div>
        </div>
    )
}

export default React.memo(Popover)
