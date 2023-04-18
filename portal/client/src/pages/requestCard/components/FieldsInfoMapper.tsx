import React from 'react'
import { IReqCardInfo } from '../../../interfaces/myrequests'
import styles from './FieldsInfoMapper.module.scss'
import { FieldTypes, UIFieldNames } from '../../../interfaces/request'
import { getFormattedDateString } from '../../../utils/formatDate'
import { CommentField } from './CommentField'

interface FieldsInfoMapperProps {
    info: IReqCardInfo
    getRequestInfo: (requestId: string) => void
}

function FieldsInfoMapper({ info, getRequestInfo }: FieldsInfoMapperProps) {
    const { requestDataFields, requestData } = info
    const flatFields = requestDataFields?.map(i => {
        const e = Object.entries(i)
        return {
            fieldName: e[0][0],
            ...e[0][1]
        }
    })

    const getUICardAction = React.useCallback(
        (fieldName: string) => {
            const fieldValue = requestData?.[fieldName]
            switch (fieldName) {
                case UIFieldNames.comment: {
                    return <CommentField commentValue={fieldValue} getRequestInfo={getRequestInfo} />
                }
            }
        },
        [getRequestInfo, requestData]
    )

    const getFormattedValue = React.useCallback(
        (f: { fieldName: string; type: any; value: string | null; isUICardAction: boolean | null }) => {
            const fieldValue = requestData?.[f.fieldName]
            if (f.isUICardAction) {
            }
            switch (f.type) {
                case FieldTypes.Boolean:
                case FieldTypes.Checkbox: {
                    return fieldValue ? 'Да' : 'Нет'
                }
                case FieldTypes.LocalDateTime: {
                    return getFormattedDateString(fieldValue, 'DD.MM.YYYY HH:mm')
                }
                case FieldTypes.Date: {
                    return getFormattedDateString(fieldValue, 'DD.MM.YYYY')
                }
                default: {
                    return fieldValue
                }
            }
        },
        [requestData]
    )

    return (
        <>
            {flatFields
                .filter(f => f.visible && f.value)
                ?.map(i => {
                    const { isUICardAction } = i
                    return (
                        <div className={styles.item} key={i.fieldName}>
                            <h3>{i.value}</h3>
                            {isUICardAction ? getUICardAction(i.fieldName) : <p>{getFormattedValue(i)}</p>}
                        </div>
                    )
                })}
        </>
    )
}

export default React.memo(FieldsInfoMapper)
