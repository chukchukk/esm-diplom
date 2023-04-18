/**
 * Hide end part string by '...'
 * Show full string on click at '...'
 */

import React from 'react'
import styles from './HiddenString.module.css'
import { DataValue } from '@tesler-ui/core/interfaces/data'

export interface HiddenStringProps {
    inputString: string | DataValue
    showLength: number
}

export const HiddenString: React.FC<HiddenStringProps> = props => {
    const { inputString, showLength } = props

    const [valueString, setValueString] = React.useState((inputString as string)?.substr(0, showLength))
    const [showed, setShowed] = React.useState(false)

    if (typeof inputString !== 'string' || !(showLength && inputString?.length > showLength)) {
        return <div>{inputString}</div>
    }

    return (
        <div>
            {valueString}
            {showed ? (
                <span>
                    <br />
                    <span
                        className={styles.pointHide}
                        onClick={() => {
                            setValueString(inputString.substr(0, showLength))
                            setShowed(false)
                        }}
                    >
                        Свернуть
                    </span>
                </span>
            ) : (
                <span
                    className={styles.pointer}
                    onClick={() => {
                        setValueString(inputString)
                        setShowed(true)
                    }}
                >
                    ...
                </span>
            )}
        </div>
    )
}

export default HiddenString
