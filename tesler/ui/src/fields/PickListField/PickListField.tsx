import React from 'react'
import { connect, useSelector } from 'react-redux'
import { BaseFieldProps, ChangeDataItemPayload } from '@tesler-ui/core/components/Field/Field'
import { Dispatch } from 'redux'
import { PickListFieldMeta, WidgetTypes } from '@tesler-ui/core/interfaces/widget'
import { PickMap } from '@tesler-ui/core/interfaces/data'
import { $do } from '@tesler-ui/core'
import { AppState } from '../../interfaces/storeSlices'
import ReadOnlyField from '../ReadOnlyField/ReadOnlyField'
import PickInput from '../../components/ui/PickInput/PickInput'
import { getAncestorByClass } from '../../utils/dom'

export type LinkedPicklistFieldMeta = PickListFieldMeta & { emptyLinkText?: string }

interface IPickListWidgetInputOwnProps extends BaseFieldProps {
    widgetName: string
    cursor: string
    disabled: boolean
    meta: LinkedPicklistFieldMeta
    value?: string
    placeholder?: string
}

interface IPickListWidgetInputProps extends IPickListWidgetInputOwnProps {
    parentBCName: string
    onChange: (payload: ChangeDataItemPayload) => void
    onClick: (bcName: string, pickMap: PickMap, widgetName?: string, parentBCName?: string, targetCursor?: string) => void
}

const PickListField: React.FunctionComponent<IPickListWidgetInputProps> = props => {
    const { meta, placeholder, onChange, value, onClick, cursor, parentBCName, disabled, readOnly } = props
    const { popupBcName: bcName, pickMap, emptyLinkText } = meta
    const popupWidget = useSelector((store: AppState) =>
        store.view.widgets.find(i => i.bcName === bcName && i.type === WidgetTypes.PickListPopup)
    )

    const handleClear = React.useCallback(() => {
        Object.keys(pickMap).forEach(field => {
            onChange({
                bcName: parentBCName,
                cursor,
                dataItem: { [field]: '' }
            })
        })
    }, [pickMap, onChange, parentBCName, cursor])

    const handleClick = React.useCallback(() => {
        onClick(bcName, pickMap, popupWidget?.name)
    }, [onClick, bcName, pickMap, popupWidget?.name])

    const handleClickReadonly = React.useCallback(
        e => {
            debugger
            const tableRow = getAncestorByClass(e.target, 'ant-table-row')
            const targetCursor = tableRow?.dataset.rowKey

            if (tableRow && targetCursor) {
                return onClick(bcName, pickMap, popupWidget?.name, parentBCName, targetCursor)
            }
            // поле находится не в таблице
            handleClick()
        },
        [onClick, bcName, pickMap, popupWidget?.name, parentBCName, handleClick]
    )

    if (readOnly) {
        if (disabled) {
            return <ReadOnlyField>{value || ''}</ReadOnlyField>
        }
        return value || !emptyLinkText ? (
            <ReadOnlyField onDrillDown={handleClickReadonly}>{value}</ReadOnlyField>
        ) : (
            <ReadOnlyField onDrillDown={handleClickReadonly}>{emptyLinkText}</ReadOnlyField>
        )
    }

    return <PickInput disabled={disabled} value={value} onClick={handleClick} onClear={handleClear} placeholder={placeholder} />
}

function mapStateToProps(state: AppState, ownProps: IPickListWidgetInputOwnProps) {
    const { widgets } = state.view
    return {
        parentBCName: widgets.find(i => i.name === ownProps.widgetName)?.bcName
    }
}

const mapDispatchToProps = (dispatch: Dispatch) => ({
    onChange: (payload: ChangeDataItemPayload) => {
        return dispatch($do.changeDataItem(payload))
    },
    onClick: (bcName: string, pickMap: PickMap, widgetName?: string, parentBCName?: string, targetCursor?: string) => {
        if (parentBCName && targetCursor) {
            dispatch(
                $do.bcChangeCursors({
                    cursorsMap: {
                        [parentBCName]: targetCursor
                    }
                })
            )
        }
        dispatch($do.showViewPopup({ bcName, widgetName }))
        dispatch($do.viewPutPickMap({ map: pickMap, bcName }))
    }
})

const ConnectedPickListField = connect(mapStateToProps, mapDispatchToProps)(PickListField as any)

export default ConnectedPickListField
