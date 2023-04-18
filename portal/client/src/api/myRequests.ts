import { IFileItem, IMyReq, IReqCardInfo } from '../interfaces/myrequests'
import { axiosInstance } from './instance'

export const getMyRequests = async (page: number, sort: { direction: string; accessor: string }, isActive: boolean) => {
    const res = await axiosInstance.get<{
        content: IMyReq[]
        totalPages: number
        totalElements: number
        first: boolean
        last: boolean
    }>(`/request/listByCurrentUser`, {
        params: {
            isActive,
            pageNumber: page,
            pageSize: 10,
            sort: sort.accessor
        }
    })
    return res.data
}

export const getMyRequest = async (requestId: string) => {
    const res = await axiosInstance.get<IReqCardInfo>(`/request/card/${requestId}`, {})
    return res.data
}
export const uploadFiles = async (requestId: string | undefined, files: FormData) => {
    const res = await axiosInstance.post<IFileItem[]>(`/api/v1/uploadRequestDataFiles/${requestId}`, files, {
        headers: { 'Content-Type': 'multipart/form-data' }
    })
    return res.data
}

export const downloadFile = async (fileId: string, preview: boolean) => {
    const res = await axiosInstance.get<{
        id: string
        source: string | null
        preview: boolean | null
    }>('/api/v1/downloadFile', {
        params: {
            id: fileId,
            source: null,
            preview: preview
        }
    })
    return res.data
}

export const downloadZip = async (requestId: string, byEmployee: boolean) => {
    const res = await axiosInstance.get<{
        id: string
        source: string | null
        preview: boolean | null
    }>('/api/v1/zip', {
        params: {
            requestId,
            byEmployee
        }
    })
    return res.data
}
