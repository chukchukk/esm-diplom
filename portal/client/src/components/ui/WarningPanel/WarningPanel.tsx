import React from 'react'

import styles from './WarningPanel.module.scss'

interface WarningPanelProps {
    title: string
    content?: React.ReactNode
}

function WarningPanel({ title, content }: WarningPanelProps) {
    return (
        <div className={styles.container}>
            <p className={styles.title}>{title}</p>
            {content}
        </div>
    )
}

export default React.memo(WarningPanel)
