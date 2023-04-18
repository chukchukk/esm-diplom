import cn from 'classnames'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { BeatLoader } from 'react-spinners'
import { getMyRequests } from '../../api/myRequests'
import CustomIcon from '../../components/CustomIcon'
import { Table } from '../../components/Table/Table'
import { usePrevious } from '../../hooks/usePrevious'
import { getFormattedDateReversed } from '../../utils/formatDate'
import styles from './MyRequests.module.scss'
import { Pagination } from './Pagination'

const columnHeaderMap = {
    requestId: '№',
    createdDate: 'Дата создания',
    legalEntityName: 'Юр. лицо',
    requestType: 'Заявка',
    status: 'Статус'
}

const columnKeys = ['createdDate', 'legalEntityName', 'requestType', 'status']

export const MyRequests: React.FC = () => {
    const [myRequests, setMyRequests] = useState<any[]>([])
    const [sort, setSort] = useState({
        direction: 'none',
        accessor: 'some_accessor'
    })

    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)
    const [isActive, setIsActive] = useState(true)
    const [loading, setLoading] = useState(true)

    const prevActive = usePrevious(isActive)
    const activeChanged = prevActive !== isActive

    const navigate = useNavigate()

    const handlePageChangeNext = () => {
        if (page + 1 === totalPages) {
            return
        }
        setPage(page => {
            return page + 1
        })
    }
    const handlePageChangePrev = () => {
        if (page === 0) {
            return
        }
        setPage(page => {
            return page - 1
        })
    }

    const handleChangeActive = (type: boolean) => {
        return () => {
            setIsActive(type)
            setPage(0)
            setTotalPages(0)
        }
    }

    const handleRowClick = (requestId: string) => {
        navigate(`${requestId}`)
    }

    useEffect(() => {
        setMyRequests([])
    }, [isActive])

    useEffect(() => {
        setLoading(true)
        getMyRequests(page, sort, isActive).then(res => {
            setMyRequests(res.content)
            setTotalPages(res.totalPages)
            setLoading(false)
        })
    }, [isActive, page, sort, sort.accessor])

    const columnHeaderClick = async (column: any) => {
        switch (column.direction) {
            case 'none':
                setSort({ direction: 'ASC', accessor: column.id })
                break
            case 'ASC':
                setSort({ direction: 'DESC', accessor: column.id })
                break
            case 'DESC':
                setSort({ direction: 'none', accessor: column.id })
                break
        }
    }

    const getMainContent = () => {
        if ((!myRequests.length && loading) || activeChanged) {
            return (
                <div className={styles.myRequestsLoading}>
                    <BeatLoader color="black" />
                </div>
            )
        } else if (!myRequests.length && !loading) {
            return (
                <div className={styles.myRequestsEmpty}>
                    <CustomIcon type="norequests" width={380} height={337} />
                    <p>Нет активных заявок</p>
                </div>
            )
        }

        return (
            <div className={styles.myRequestsList}>
                <Table data={myRequests} columns={columns} onHeaderClick={columnHeaderClick} handleRowClick={handleRowClick} />
                <Pagination
                    totalPages={totalPages}
                    currentPage={page}
                    handleNextClick={handlePageChangeNext}
                    handlePrevClick={handlePageChangePrev}
                    loading={loading}
                />
            </div>
        )
    }

    const columns =
        myRequests.length &&
        columnKeys.map(key => {
            return {
                accessor: key,
                Header: columnHeaderMap[key as keyof typeof columnHeaderMap],
                sortType: 'basic',
                direction: sort.accessor === key ? sort.direction : 'none',
                ...(key === 'createdDate' && {
                    Cell: (props: any) => {
                        if (key === 'createdDate') {
                            return getFormattedDateReversed(props.value)
                        }
                        return props.value
                    }
                })
            }
        })

    return (
        <div className="sectionOuter">
            <div className={cn('sectionInner', styles.myRequestsContainer)}>
                <h2>Мои заявки</h2>
                <div className={styles.myRequests}>
                    {getMainContent()}
                    <div className={styles.myRequestsSide}>
                        <button className={cn({ [styles.myRequestsSideActive]: isActive })} onClick={handleChangeActive(true)}>
                            Заявки в работе
                        </button>
                        <button className={cn({ [styles.myRequestsSideActive]: !isActive })} onClick={handleChangeActive(false)}>
                            Архив заявок
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}
