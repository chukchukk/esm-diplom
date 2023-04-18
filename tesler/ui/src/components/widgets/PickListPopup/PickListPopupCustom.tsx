import { $do, buildBcUrl, ColumnTitle, Pagination, Popup } from '@tesler-ui/core'
import { ChangeDataItemPayload } from '@tesler-ui/core/components/Field/Field'
import { DataItem, PendingDataItem, PickMap } from '@tesler-ui/core/interfaces/data'
import { RowMetaField } from '@tesler-ui/core/interfaces/rowMeta'
import { FieldType } from '@tesler-ui/core/interfaces/view'
import { PaginationMode, WidgetTableMeta } from '@tesler-ui/core/interfaces/widget'
import { Skeleton, Spin, Table } from 'antd'
import { ColumnProps } from 'antd/es/table'
import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { AppState } from '../../../interfaces/storeSlices'
import styles from './PickListPopup.module.css'
import cn from 'classnames'
import { PendingRequest } from '@tesler-ui/core/interfaces/session'
import { PopupProps } from '@tesler-ui/core/components/ui/Popup/Popup'
import { $do as $customDo } from '../../../actions/types'

export interface PickListPopupOwnProps extends Omit<PopupProps, 'bcName' | 'children' | 'showed'> {
    widget: WidgetTableMeta
    className?: string
    components?: {
        title?: React.ReactNode
        table?: React.ReactNode
        footer?: React.ReactNode
    }
    disableScroll?: boolean
}

export const PickListPopupCustom: React.FC<PickListPopupOwnProps> = ({ widget, components, className, disableScroll }) => {
    const bcName = widget.bcName
    const bcUrl = buildBcUrl(bcName, true)
    const rowMeta = useSelector((state: AppState) => state.view.rowMeta)
    const rowMetaFields = rowMeta[bcName]?.[bcUrl]?.fields
    const bc = useSelector((state: AppState) => state.screen.bo.bc[bcName])
    const parentBCName = bc?.parentName || ''
    const parentBc = useSelector((state: AppState) => state.screen.bo.bc[parentBCName])
    const pickMap = useSelector((state: AppState) => state.view.pickMap) as PickMap
    const data = useSelector((state: AppState) => state.data[widget.bcName])
    const cursor = useSelector((state: AppState) => state.screen.bo.bc[parentBCName]?.cursor)
    const bcLoading = bc.loading
    const pending = useSelector((state: AppState) =>
        state.session.pendingRequests?.filter(item => item.type === 'force-active')
    ) as PendingRequest[]

    const dispatch = useDispatch()

    const onChange = React.useCallback(
        (payload: ChangeDataItemPayload) => {
            const parentPage = parentBc.page
            dispatch($customDo.setKeepBcPage({ page: parentPage || 0 }))
            dispatch($do.changeDataItem(payload))
        },
        [dispatch, parentBc.page]
    )

    const onClose = () => {
        dispatch($do.viewClearPickMap(null))
        dispatch($do.closeViewPopup({ bcName }))
    }

    const onRow = React.useCallback(
        (rowData: DataItem) => {
            return {
                onClick: (e: React.MouseEvent<Element, MouseEvent>) => {
                    if (cursor) {
                        const dataItem: PendingDataItem = {}
                        Object.keys(pickMap).forEach(field => {
                            dataItem[field] = rowData[pickMap[field]]
                        })
                        onChange({
                            bcName: parentBCName,
                            cursor,
                            dataItem
                        })
                    }
                }
            }
        },
        [pickMap, onChange, parentBCName, cursor]
    )

    const defaultTitle = React.useMemo(
        () => (
            <div>
                <h1 className={styles.title}>{widget.title}</h1>
            </div>
        ),
        [widget.title]
    )
    const title = components?.title === undefined ? defaultTitle : components.title

    const defaultFooter = React.useMemo(
        () => (
            <div className={styles.footerContainer}>
                {!widget.options?.hierarchyFull && (
                    <div className={styles.pagination}>
                        <Pagination bcName={widget.bcName} mode={PaginationMode.page} widgetName={widget.name} />
                    </div>
                )}
            </div>
        ),
        [widget.options?.hierarchyFull, widget.bcName, widget.name]
    )
    const footer = components?.footer === undefined ? defaultFooter : components.footer

    const columns: Array<ColumnProps<DataItem>> = widget.fields
        .filter(item => item.type !== FieldType.hidden && !item.hidden)
        .map(item => {
            const fieldRowMeta = rowMetaFields?.find(field => field.key === item.key) as RowMetaField
            return {
                title: <ColumnTitle widgetName={widget.name} widgetMeta={item} rowMeta={fieldRowMeta} />,
                key: item.key,
                dataIndex: item.key,
                render: (text, dataItem) => {
                    return text
                }
            }
        })

    const defaultTable = (
        <div>
            <Table className={styles.table} columns={columns} dataSource={data} rowKey="id" onRow={onRow} pagination={false} />
        </div>
    )

    const table = bcLoading ? (
        <Skeleton loading paragraph={{ rows: 5 }} />
    ) : components?.table === undefined ? (
        defaultTable
    ) : (
        components.table
    )

    return (
        <Popup
            title={title}
            size="large"
            showed
            onOkHandler={onClose}
            onCancelHandler={onClose}
            bcName={widget.bcName}
            widgetName={widget.name}
            disablePagination={widget.options?.hierarchyFull}
            footer={footer}
            className={cn(styles.container, className, { [styles.disableScroll]: disableScroll })}
        >
            <Spin spinning={pending?.length > 0}>{table}</Spin>
        </Popup>
    )
}
