import React, { useRef, useState } from 'react'
import styles from '../RequestCard.module.scss'
import { postComment } from '../../../api/request'
import { useParams } from 'react-router-dom'

interface CommentFieldProps {
    commentValue: string
    getRequestInfo: (requestId: string) => void
}

const MIN_TEXTAREA_HEIGHT = 20

export const CommentField: React.FC<CommentFieldProps> = ({ commentValue, getRequestInfo }) => {
    const { requestId } = useParams()

    const [comment, setComment] = useState(commentValue)
    const textareaRef = useRef<HTMLTextAreaElement>(null)
    const showCommentButtons = !!comment?.length

    const handleCommentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setComment(e.target.value)
    }
    const handleCommentSubmit = () => {
        if (requestId) {
            postComment(requestId, comment).then(res => {
                getRequestInfo(requestId)
                setComment('')
            })
        }
    }
    const handleCommentReset = () => {
        setComment('')
    }

    const autoSize = React.useCallback(() => {
        if (!textareaRef.current) {
            return
        }
        textareaRef.current.style.height = `${MIN_TEXTAREA_HEIGHT}px`
        textareaRef.current.style.height = `${Math.max(textareaRef.current.scrollHeight, MIN_TEXTAREA_HEIGHT)}px`
    }, [])

    React.useLayoutEffect(() => {
        autoSize()
    }, [comment, autoSize])

    return (
        <div className={styles.textAreaContainer}>
            <textarea ref={textareaRef} value={comment || ''} onChange={handleCommentChange} placeholder="Введи текст" />
            {showCommentButtons && (
                <>
                    <button className="secondaryLink" onClick={handleCommentSubmit}>
                        Отправить
                    </button>
                    <button className="secondaryLink" onClick={handleCommentReset}>
                        Отменить
                    </button>
                </>
            )}
        </div>
    )
}
