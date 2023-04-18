import React from 'react'
import styles from './Loader.module.scss'
import { BeatLoader } from 'react-spinners'

function Loader() {
    return (
        <div className={styles.loadingContainer}>
            <BeatLoader color="black" />
        </div>
    )
}

export default Loader
