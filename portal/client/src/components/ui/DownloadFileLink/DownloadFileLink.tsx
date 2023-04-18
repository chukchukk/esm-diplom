import React from 'react'

import styles from './DownloadFileLink.module.scss'
import { useLazyDownloadFileQuery } from '../../../store/portalService'
import convertToBlobAndDownload from '../../../utils/convertToBlobAndDownload'

interface DownloadFileLinkProps {
    fileId: string
    preview?: boolean
    fileName: string
}

function DownloadFileLink({ fileName, fileId, preview = false }: DownloadFileLinkProps) {
    const [download] = useLazyDownloadFileQuery()
    return (
        <button
            type="button"
            className={styles.link}
            onClick={() =>
                download({ fileId, preview }).then(res => {
                    if (res.data) {
                        convertToBlobAndDownload(res.data.file, res.data.fileName)
                    }
                })
            }
        >
            {fileName}
        </button>
    )
}

export default React.memo(DownloadFileLink)
