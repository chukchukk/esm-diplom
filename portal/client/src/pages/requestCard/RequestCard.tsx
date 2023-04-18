import React, { useCallback, useEffect, useState, useMemo } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { getMyRequest, uploadFiles } from '../../api/myRequests'
import cn from 'classnames'
import styles from './RequestCard.module.scss'
import { IFileItem, IReqCardInfo } from '../../interfaces/myrequests'
import CustomIcon from '../../components/CustomIcon'
import { BeatLoader } from 'react-spinners'
import { getFormattedDateReversed, getFormattedFullDate } from '../../utils/formatDate'
import { statusColorMap, statusColorText } from '../../components/Table/Table'
import { postCancel, postSendToWork } from '../../api/request'
import FieldsInfoMapper from './components/FieldsInfoMapper'
import { __API__ } from '../../api/instance'
import { Modal } from '../../components/Modal/Modal'
import { RequestCategory } from '../../interfaces/request'
import WarningPanel from '../../components/ui/WarningPanel/WarningPanel'

export const RequestCard: React.FC = () => {
    const { requestId } = useParams()
    const navigate = useNavigate()
    const [files, setFiles] = useState<File[] | null>()
    const [showInput, setShowInput] = useState<boolean>(false)
    const [showModal, setShowModal] = useState(false)
    const [currentFiles, setCurrentFiles] = useState<IFileItem[] | null>(null)

    const handleSubmitFiles = useCallback(() => {
        var data = new FormData()
        files?.forEach(file => {
            data.append('files', file)
        })
        uploadFiles(requestId, data).then(newUploaded => {
            setFiles(null)
            setCurrentFiles(newUploaded)
        })
    }, [files, requestId])

    const handleModalOpen = () => {
        setShowModal(true)
    }
    const handleModalClose = () => {
        setShowModal(false)
    }

    const onInputClick = (e: any) => {
        e.target.value = ''
    }

    const handleRemoveFile = (e: React.MouseEvent<HTMLElement>) => {
        const deletingIdx = +e.currentTarget.id
        const oldFiles = [...(files || [])]
        oldFiles?.splice(deletingIdx, 1)
        setFiles(oldFiles)
    }

    const handleToggleShowInput = () => {
        setFiles(null)
        setShowInput(prev => !prev)
    }

    const [requestInfo, setRequestInfo] = useState<IReqCardInfo | null>(null)
    const [loading, setLoading] = useState<boolean>(true)

    useEffect(() => {
        getRequestInfo(requestId || '')
    }, [requestId])

    const getRequestInfo = (requestId: string) => {
        getMyRequest(requestId)
            .then(res => {
                setRequestInfo(res)
                setLoading(false)
            })
            .catch(err => setLoading(true))
    }

    const handleMyRequestsRedirect = useCallback(() => {
        navigate('/my-requests')
    }, [navigate])

    const handleEditClick = () => {
        switch (requestInfo?.requestData.type.category) {
            case RequestCategory.PERSONAL_DATA_CHANGE: {
                navigate(`/personal-data-change/${requestId}`)
                break
            }
            case RequestCategory.DOCUMENT: {
                navigate(`/document/${requestId}`)
                break
            }
            default: {
                navigate(`/my-requests/edit/${requestId}`)
            }
        }
    }

    const handleCancelClick = useCallback(async () => {
        if (!requestId) {
            return
        }
        setShowModal(false)
        setLoading(true)
        try {
            await postCancel(requestId)
            handleMyRequestsRedirect()
        } catch (error) {
            console.log(error)
        }
    }, [handleMyRequestsRedirect, requestId])

    const handleTakeToWorkClick = async () => {
        if (!requestId) {
            return
        }
        setLoading(true)
        try {
            await postSendToWork(requestId)
            handleMyRequestsRedirect()
        } catch (error) {
            console.log(error)
        }
    }

    const requestCardButtonsMap = (type: string) => {
        switch (type) {
            case 'cancelButton': {
                return (
                    <button className="button cancelButton" onClick={handleModalOpen} key={type}>
                        Отменить заявку
                    </button>
                )
            }
            case 'sendToWorkButton': {
                return (
                    <button className="button" onClick={handleTakeToWorkClick} key={type}>
                        Вернуть в работу
                    </button>
                )
            }
            case 'editButton': {
                return (
                    <button className="button cancelButton" onClick={handleEditClick} key={type}>
                        Редактировать
                    </button>
                )
            }
        }
    }
    const fileActionsMap = (type: string, files?: IFileItem[]) => {
        switch (type) {
            case 'addFilesButton': {
                return (
                    <button className={styles.fileActionButton} onClick={handleToggleShowInput} key={type}>
                        <p>Добавить файл</p>
                        <CustomIcon type="add" />
                    </button>
                )
            }
            case 'downloadZip': {
                return (
                    <a
                        className={cn(styles.fileActionButton, {
                            [styles.fileActionButtonDisabled]: !files?.length
                        })}
                        target="_blank"
                        rel="noreferrer"
                        href={`${__API__}zip?requestId=${requestId}&byEmployee=true`}
                        key={type}
                    >
                        <p>Скачать все документы</p>
                        <CustomIcon type={files?.length ? 'download' : 'downloadDisabled'} width={20} height={20} />
                    </a>
                )
            }
        }
    }

    const headerInfo = requestInfo?.requestHeader
    const historyInfo = requestInfo?.requestApprovalHistory
    const employeeFiles = currentFiles || requestInfo?.requestFiles.employeeFiles.files
    const implementerFiles = requestInfo?.requestFiles.implementerFiles.files
    const employeeActions = requestInfo?.requestFiles.employeeFiles.actions
    const implementerActions = requestInfo?.requestFiles.implementerFiles.actions
    const requestCardButtons = requestInfo?.requestCardButtons

    const statusColor = headerInfo?.status && statusColorMap[headerInfo?.status as keyof typeof statusColorMap]
    const statusColorDark = headerInfo?.status && statusColorText[headerInfo?.status as keyof typeof statusColorText]

    const modalFooter = useMemo(
        () => (
            <>
                <button className="button cancelButtonSolid" onClick={handleModalClose}>
                    Вернуться
                </button>
                <button className="button" onClick={handleCancelClick}>
                    Подтвердить
                </button>
            </>
        ),
        [handleCancelClick]
    )

    return (
        <div className={'sectionOuterGradient'}>
            <div className={cn('sectionInner', styles.requestCardContainer)}>
                <Modal show={showModal} onClose={handleModalClose} title="Отмена заявки" footer={modalFooter}>
                    Ты точно хочешь <b>отменить заявку?</b>
                </Modal>
                <h2>Заявка</h2>

                <div className={styles.mainContent}>
                    {!loading ? (
                        <div className={styles.requestCard}>
                            <div className={styles.requestCardHeader}>
                                <div>{getFormattedDateReversed(headerInfo?.createdDate || '')}</div>
                                {headerInfo?.legalEntityName && <div>{headerInfo.legalEntityName}</div>}
                                <div>{headerInfo?.type}</div>
                                <div
                                    style={{
                                        backgroundColor: statusColor,
                                        color: statusColorDark
                                    }}
                                    className="statusItem"
                                >
                                    {headerInfo?.status}
                                </div>
                            </div>
                            <div className={styles.requestCardContent}>
                                {requestInfo && <FieldsInfoMapper info={requestInfo} getRequestInfo={getRequestInfo} />}
                                {requestInfo?.hint && (
                                    <div className={styles.requestCardContentHint}>
                                        <WarningPanel title={requestInfo?.hint} />
                                    </div>
                                )}
                                {!!requestCardButtons?.length && (
                                    <div className={styles.requestCardContentButtons}>
                                        {requestCardButtons.map(buttonName => requestCardButtonsMap(buttonName))}
                                    </div>
                                )}
                            </div>
                            <div className={styles.requestCardFiles}>
                                <div className={styles.requestCardFilesHeader}>
                                    <CustomIcon type="file" width={18} height={18} />
                                    <h3>Документы</h3>
                                </div>

                                <div className={styles.requestCardFilesSubHeader}>
                                    <h4>Файлы заявителя</h4>
                                </div>

                                {!!employeeFiles?.length &&
                                    employeeFiles.map((item, idx) => {
                                        return (
                                            <div className={styles.requestCardFilesContainer} key={idx}>
                                                <div className={styles.filterContainer}>{item.fileName}</div>
                                                <div className={styles.requestCardFilesRight}>
                                                    <a
                                                        className="secondaryLink"
                                                        target="_blank"
                                                        rel="noreferrer"
                                                        href={`${__API__}downloadFile?id=${item.fileId}&preview=true`}
                                                    >
                                                        <CustomIcon type="visibility" width={24} height={24} />
                                                        Посмотреть
                                                    </a>
                                                    <a
                                                        className="secondaryLink"
                                                        target="_blank"
                                                        rel="noreferrer"
                                                        href={`${__API__}downloadFile?id=${item.fileId}&preview=false`}
                                                        download
                                                    >
                                                        <CustomIcon type="download" width={24} height={24} />
                                                        Скачать
                                                    </a>
                                                </div>
                                            </div>
                                        )
                                    })}
                                <div className={styles.uploadContainer}>
                                    {showInput && (
                                        <>
                                            <div className={styles.uploadContainerInputs}>
                                                <div className={styles.uploadContainerInputsFilters}>
                                                    {files?.map((file, idx) => (
                                                        <div className={styles.filterContainer} key={idx}>
                                                            {file.name}
                                                            <button id={'' + idx} onClick={handleRemoveFile}>
                                                                <CustomIcon type="clear" />
                                                            </button>
                                                        </div>
                                                    ))}
                                                </div>
                                                <label htmlFor="upload">
                                                    <CustomIcon type="add" width={15} height={15} />
                                                    <input
                                                        multiple
                                                        name="files"
                                                        type={'file'}
                                                        id="upload"
                                                        onClick={onInputClick}
                                                        onChange={f => {
                                                            if (f.target.files !== null) {
                                                                const fileList = Array.prototype.slice.call(f.target.files) as File[]
                                                                setFiles(prev => {
                                                                    const prevFiles = prev || []
                                                                    return [...prevFiles, ...fileList]
                                                                })
                                                            }
                                                        }}
                                                    />
                                                </label>
                                            </div>

                                            <div className={styles.uploadContainerControls}>
                                                <button className="secondaryLink" onClick={handleSubmitFiles}>
                                                    Прикрепить
                                                </button>
                                                <button className="secondaryLink" onClick={handleToggleShowInput}>
                                                    Отменить
                                                </button>
                                            </div>
                                        </>
                                    )}

                                    {!showInput && employeeActions?.map(action => fileActionsMap(action, employeeFiles))}
                                </div>
                                <div className={styles.requestCardFilesSubHeader}>
                                    <h4>Файлы исполнителя</h4>
                                </div>

                                {!!implementerFiles?.length &&
                                    implementerFiles.map((item, idx) => {
                                        return (
                                            <div className={styles.requestCardFilesContainer} key={idx}>
                                                <div className={styles.filterContainer}>{item.fileName}</div>
                                                <div className={styles.requestCardFilesRight}>
                                                    <a
                                                        className="secondaryLink"
                                                        target="_blank"
                                                        rel="noreferrer"
                                                        href={`${__API__}file?id=${item.fileId}&preview=true`}
                                                    >
                                                        <CustomIcon type="visibility" width={24} height={24} />
                                                        Посмотреть
                                                    </a>
                                                    <a
                                                        className="secondaryLink"
                                                        target="_blank"
                                                        rel="noreferrer"
                                                        href={`${__API__}file?id=${item.fileId}&preview=false`}
                                                        download
                                                    >
                                                        <CustomIcon type="download" width={24} height={24} />
                                                        Скачать
                                                    </a>
                                                </div>
                                            </div>
                                        )
                                    })}

                                <div className={styles.uploadContainer}>
                                    {implementerActions?.map(action => fileActionsMap(action, implementerFiles))}
                                </div>
                            </div>

                            <div className={styles.requestCardHistory}>
                                <div className={styles.requestCardHistoryHeader}>
                                    <CustomIcon type="history" width={18} height={18} />
                                    <h3>Жизненный цикл заявки</h3>
                                </div>

                                {historyInfo && (
                                    <table className={styles.requestCardHistoryTable}>
                                        <thead>
                                            <tr>
                                                <th>Дата и время</th>
                                                <th>Действия с заявкой</th>
                                                <th>Автор действия</th>
                                                <th>Комментарий</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {historyInfo.map((item, idx) => {
                                                return (
                                                    <tr key={idx}>
                                                        <td>{getFormattedFullDate(item.createdDate)}</td>
                                                        <td>{item.actionType}</td>
                                                        <td>{<a className="secondaryLink">{item.actionAuthor}</a>}</td>
                                                        <td>{item.comment}</td>
                                                    </tr>
                                                )
                                            })}
                                        </tbody>
                                    </table>
                                )}
                            </div>
                        </div>
                    ) : (
                        <div className={styles.emptyCard}>
                            <BeatLoader color="black" />
                        </div>
                    )}

                    <button className={styles.sideButton} onClick={handleMyRequestsRedirect}>
                        К ЗАЯВКАМ
                    </button>
                </div>
            </div>
        </div>
    )
}
