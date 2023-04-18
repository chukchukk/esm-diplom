import { buildBcUrl, $do, Popup, FormWidget } from '@tesler-ui/core'
import { Operation, isOperationGroup } from '@tesler-ui/core/interfaces/operation'
import { WidgetFieldBase, WidgetFormMeta, WidgetTypes } from '@tesler-ui/core/interfaces/widget'
import { Skeleton } from 'antd'
import React, { useRef, useEffect, useCallback } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { AppState } from '../../../interfaces/storeSlices'
import styles from './FormPopupWidget.module.css'
import { OperationCustom, OperationGroupCustom, OperationPreInvokeCustom } from '../../../interfaces/operation'

import { WidgetFormPopupMeta } from '../../../interfaces/widget'
import { useForceUpdate } from '../../../hooks/useForceUpdate'
import { $do as $customDo } from '../../../actions/types'

export interface IFormPopupProps {
    meta: WidgetFormPopupMeta
}

// function isOperationGroup(operation: OperationCustom | OperationGroupCustom): operation is OperationGroupCustom {
//     return Array.isArray((operation as OperationGroupCustom).actions)
// }

export function FormPopupWidget(props: IFormPopupProps) {
    const dispatch = useDispatch()

    const formContainerRef = useRef<HTMLDivElement>(null)
    const fileInputRows = useRef<Element[]>([])
    const wasSaveAttempt = useRef<boolean>(false)

    const forceUpdate = useForceUpdate()

    const bcName = props.meta.bcName
    const bc = useSelector((state: AppState) => state.screen.bo.bc[bcName])

    const bcCursor = (bc && bc.cursor) || ''
    const calleeBCName = useSelector((state: AppState) => state.view.popupData?.calleeBCName) || ''
    const bcUrl = buildBcUrl(calleeBCName, true)
    const showed = !!useSelector((state: AppState) => state.view.popupData?.bcName === bcName)
    const assocValueKey = useSelector((state: AppState) => state.view.popupData?.assocValueKey)

    const bcLoading = bc && bc.loading
    const caleeCursor = useSelector((state: AppState) => state.screen.bo.bc[calleeBCName]?.cursor)

    const parentName = bc?.parentName
    useEffect(() => {
        if (!caleeCursor || bcLoading || !parentName) {
            return
        }
        dispatch(
            $do.bcChangeCursors({
                cursorsMap: {
                    [bcName]: caleeCursor
                }
            })
        )
        dispatch($customDo.forceChangeWidgetCursor({ bcName: parentName, cursor: caleeCursor }))
    }, [bcLoading, bcName, caleeCursor, dispatch, parentName])

    useEffect(() => {
        if (!bcLoading) {
            const fileInputs = formContainerRef.current?.querySelectorAll('div[class^=FileUpload__fileUpload]')
            fileInputs?.forEach(el => {
                const closest = el.closest('div[class=ant-row]')
                if (closest) {
                    fileInputRows.current.push(closest)
                }
            })
        }
    }, [bcLoading])

    const rowMeta = useSelector((state: AppState) => state.view.rowMeta)

    const actions = bcUrl && rowMeta[calleeBCName] && rowMeta[calleeBCName][bcUrl] && rowMeta[calleeBCName][bcUrl].actions
    const operations: Operation[] = []
    if (actions) {
        actions.forEach((item: OperationCustom | OperationGroupCustom) => {
            if (isOperationGroup(item)) {
                item.actions.forEach(actionItem => {
                    operations.push(actionItem)
                })
            } else {
                operations.push(item)
            }
        })
    }
    const operation = operations.find(item => item.type === assocValueKey)

    const type = (showed && assocValueKey) || ''

    const onClose = useCallback(
        (bcName: string, cursor: string, fieldKey: string) => {
            dispatch($do.bcCancelPendingChanges({ bcNames: [bcName] }))
            dispatch($do.closeViewPopup({ bcName }))
        },
        [dispatch]
    )
    const onSave = useCallback(
        (bcName: string, type: string, widgetName: string) => {
            wasSaveAttempt.current = true
            if (!caleeCursor || !parentName) {
                return
            }

            forceUpdate()
            dispatch(
                $do.sendOperation({
                    bcName,
                    operationType: type,
                    widgetName,
                    confirm: 'true'
                })
            )
        },
        [caleeCursor, dispatch, forceUpdate, parentName]
    )

    const widgetName = props.meta.name

    const saveData = React.useCallback(() => {
        if (!bcLoading) {
            onSave(bcName, type, widgetName)
        }
    }, [onSave, bcLoading, bcName, type, widgetName])

    const cancelData = React.useCallback(() => {
        wasSaveAttempt.current = false
        fileInputRows.current = []
        onClose(bcName, bcCursor, (props.meta.fields[0] as WidgetFieldBase).key)
    }, [onClose, bcCursor, bcName, props.meta.fields])

    const formMeta: WidgetFormMeta = {
        ...props.meta,
        type: WidgetTypes.Form
    }

    // Наименование кнопки в попапе по приоритету:
    // operation.preInvoke.buttonOkText
    // operation.text
    // Значение по умолчанию в popup ('Выбрать')
    const buttonText = (operation?.preInvoke as OperationPreInvokeCustom)?.buttonOkText
        ? (operation?.preInvoke as OperationPreInvokeCustom)?.buttonOkText
        : operation?.text
        ? operation.text
        : 'Выбрать'

    if (!showed) {
        return null
    }

    return (
        <Popup
            title={props.meta.title}
            showed
            size="large"
            onOkHandler={saveData}
            onCancelHandler={cancelData}
            bcName={bcName}
            widgetName={props.meta.name}
            disablePagination={true}
            defaultOkText={buttonText}
        >
            {bcLoading ? (
                <Skeleton loading paragraph={{ rows: 5 }} />
            ) : (
                <div className={styles.formPopupModal} ref={formContainerRef}>
                    <FormWidget meta={formMeta} />
                </div>
            )}
        </Popup>
    )
}
