import { IGetStartResponse } from '../interfaces/request'
import { axiosInstance } from './instance'

export const postCreate = async (formData: any, typeCategory: string) => {
    const body = { ...formData, type: typeCategory }
    const res = await axiosInstance.post<IGetStartResponse>(`/request/create`, body)
    return res.data
}

export const postCancel = async (requestId: string) => {
    const res = await axiosInstance.post<IGetStartResponse>(`/request/cancel/${requestId}`)
    return res.data
}

export const postSendToWork = async (requestId: string) => {
    const res = await axiosInstance.post<IGetStartResponse>(`/request/sendToWork/${requestId}`)
    return res.data
}

export const postComment = async (id: string, comment: string) => {
    const formData = new FormData()
    formData.append('comment', comment)
    await axiosInstance.post<void>(`/request/comment/${id}`, formData)
}
