import React, { memo } from 'react'

import styles from './SimpleText.module.scss'

interface SimpleTextProps {
    text: string
    width?: number
}

function SimpleText({ text, width }: SimpleTextProps) {
    const customStyle = width && width >= 0 ? { width: `${width}px` } : undefined
    return (
        <div className={styles.content} style={customStyle}>
            {text}
        </div>
    )
}

export default memo(SimpleText)
