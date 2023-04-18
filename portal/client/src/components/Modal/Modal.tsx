import React from 'react'
import ReactDOM from 'react-dom'
import CustomIcon from '../CustomIcon'
import styles from './Modal.module.scss'

interface ModalProps {
    show: boolean
    onClose: () => void
    onConfirm?: () => void
    title: string
    children?: React.ReactNode
    footer?: React.ReactNode
}

export const Modal: React.FC<ModalProps> = ({ show, onClose, onConfirm, title, children, footer }) => {
    if (!show) {
        return null
    }
    return ReactDOM.createPortal(
        <div className={styles.modal} onClick={onClose}>
            <div className={styles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={styles.modalHeader}>
                    {title}
                    <button onClick={onClose}>
                        <CustomIcon type="clear" width={14} height={14} />
                    </button>
                </div>
                <div className={styles.modalBody}>{children}</div>
                <div className={styles.modalFooter}>{footer}</div>
            </div>
        </div>,
        document.getElementById('root') as Element
    )
}
