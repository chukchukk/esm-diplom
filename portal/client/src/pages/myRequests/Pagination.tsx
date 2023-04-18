import React from 'react'
import { BeatLoader } from 'react-spinners'
import CustomIcon from '../../components/CustomIcon'
import styles from './MyRequests.module.scss'
import cn from 'classnames'

interface PaginationProps {
    handleNextClick: () => void
    handlePrevClick: () => void
    currentPage: number
    totalPages: number
    loading: boolean
}

export const Pagination: React.FC<PaginationProps> = ({ handleNextClick, handlePrevClick, currentPage, totalPages, loading }) => {
    return (
        <div className={styles.pagination}>
            <button
                onClick={handlePrevClick}
                className={cn('button', { [styles.disabledButton]: currentPage === 0 })}
                style={{ transform: 'rotate(90deg)' }}
            >
                <CustomIcon type="arrow" />
            </button>
            <button
                onClick={handleNextClick}
                className={cn('button', {
                    [styles.disabledButton]: currentPage + 1 === totalPages
                })}
                style={{ transform: 'rotate(-90deg)' }}
            >
                <CustomIcon type="arrow" />
            </button>
            {loading && (
                <div className={styles.loading}>
                    <BeatLoader color="black" size={5} />
                </div>
            )}
            <div className={styles.paginationRight}>
                Страница {currentPage + 1}/{totalPages}
            </div>
        </div>
    )
}
