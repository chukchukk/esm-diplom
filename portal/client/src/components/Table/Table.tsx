import React from 'react'
import { useSortBy, useTable } from 'react-table'
import CustomIcon from '../CustomIcon'
import styles from './Table.module.scss'

interface TableProps {
    data: Array<any>
    onHeaderClick: any
    columns: any
    handleRowClick: (e: any) => void
}

export const statusColorMap = {
    Новая: '#FFECB4',
    'В работе': '#BAECE0',
    'Отправлена в работу': '#BAECE0',
    Выполнена: '#BFDCFB',
    Отклонена: '#FEC6A3',
    Отменена: '#FEC6A3',
    'Дозапрос информации': '#FFECB4'
}
export const statusColorText = {
    Новая: '#FF9900',
    'В работе': '#00A887',
    'Отправлена в работу': '#00A887',
    Выполнена: '#008AF5',
    Отклонена: '#F66408',
    Отменена: '#F66408',
    'Дозапрос информации': '#FF9900'
}
export const Table: React.FC<TableProps> = ({ data, onHeaderClick, columns, handleRowClick }) => {
    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
        // @ts-ignore
        useTable({ columns, data, manualSortBy: true }, useSortBy)

    return (
        <table {...getTableProps()} className={styles.table}>
            <thead>
                {headerGroups.map(headerGroup => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th
                                // @ts-ignore
                                {...column.getHeaderProps(column.getSortByToggleProps())}
                                onClick={() => onHeaderClick(column)}
                            >
                                <div className={styles.thContainer}>
                                    {column.render('Header')}
                                    <span className={styles.sortIcon}>
                                        {/* @ts-ignore */}
                                        {column.direction === 'ASC' ? (
                                            <CustomIcon type="arrow" />
                                        ) : // @ts-ignore
                                        column.direction === 'DESC' ? (
                                            <CustomIcon type="arrow" className={styles.downArrow} />
                                        ) : null}
                                    </span>
                                </div>
                            </th>
                        ))}
                    </tr>
                ))}
            </thead>
            <tbody {...getTableBodyProps()}>
                {rows.map((row, index) => {
                    prepareRow(row)
                    return (
                        <tr
                            {...row.getRowProps()}
                            className={styles.tableRow}
                            onClick={() => {
                                handleRowClick(data[index].requestId)
                            }}
                        >
                            {row.cells.map(cell => {
                                const renderedCell = cell.render('Cell')
                                if (cell.column.id === 'status') {
                                    const statusValue = data[index]?.status
                                    const statusColor = statusColorMap[statusValue as keyof typeof statusColorMap]
                                    const darkerColor = statusColorText[statusValue as keyof typeof statusColorText]

                                    return (
                                        <td key={data[index].id + cell.column.id}>
                                            <div
                                                className="statusItem"
                                                style={{
                                                    backgroundColor: statusColor,
                                                    color: darkerColor
                                                }}
                                            >
                                                {renderedCell}
                                            </div>
                                        </td>
                                    )
                                } else if (cell.column.id === 'legalEntityName') {
                                    return (
                                        <td key={data[index].id + cell.column.id}>
                                            <div
                                                className="styles.tableCell"
                                                style={{
                                                    whiteSpace: 'nowrap'
                                                }}
                                            >
                                                {renderedCell}
                                            </div>
                                        </td>
                                    )
                                }

                                return (
                                    <td {...cell.getCellProps()} className={styles.tableCell}>
                                        {renderedCell}
                                    </td>
                                )
                            })}
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}
