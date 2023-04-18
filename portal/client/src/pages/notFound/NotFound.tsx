import React from 'react'
import styles from './NotFound.module.scss'

export const NotFound: React.FC = () => {
    return (
        <>
            <div className={styles.NotFound}>
                <div className={styles.container}>
                    <div className={styles.info}>
                        <div className={styles.title}>К сожалению, страница не найдена</div>
                        <div className={styles.description}>Неправильно набран адрес, или такой страницы на сайте больше не существует</div>
                    </div>
                </div>
            </div>
        </>
    )
}
