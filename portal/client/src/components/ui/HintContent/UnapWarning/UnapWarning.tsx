import React from 'react'
import { requestByCategory } from '../../../../interfaces/request'

import styles from './UnapWarning.module.scss'
import { Link } from 'react-router-dom'
import { REQUEST_TYPE_QUERY_PARAMETER } from '../../../../constants/text'

function UnapWarning() {
    return (
        <p className={styles.content}>
            При подписании документов СМС с кодом будут приходить на номер телефона, указанный в заявлении на выпуск сертификата. Ты можешь
            поменять номер телефона, сделав заявку на{' '}
            <Link
                className={styles.link}
                to={{
                    pathname: '/personal-data-change',
                    search: `?${REQUEST_TYPE_QUERY_PARAMETER}=${requestByCategory.PERSONAL_DATA_CHANGE.PHONE_NUMBER_CHANGE}`
                }}
                target="_blank"
                rel="noreferrer"
            >
                изменение номера телефона
            </Link>
            .
        </p>
    )
}

export default UnapWarning
