import { createApi } from '@reduxjs/toolkit/query/react'
import { Dictionaries, RequestCategory } from '../interfaces/request'
import { axiosBaseQuery } from '../api/instance'
import { RequestData } from '../interfaces/myrequests'

export const esmService = createApi({
    reducerPath: 'esmService',
    baseQuery: axiosBaseQuery({ baseUrl: '/request' }),
    tagTypes: ['Req'],
    endpoints: builder => ({
        getDictionaries: builder.query<Dictionaries, keyof typeof RequestCategory>({
            query: type => `/dictionaries/${type}`
        }),
        getRequest: builder.query<RequestData, string>({
            providesTags: ['Req'],
            query: requestId => `/edit/${requestId}`
        }),
        editRequest: builder.mutation<number, { data: any; requestId: string }>({
            invalidatesTags: ['Req'],
            query: ({ data, requestId }) => ({
                method: 'PUT',
                url: `/${requestId}`,
                body: data
            })
        }),
        createRequest: builder.mutation<number, { data: any }>({
            invalidatesTags: ['Req'],
            query: ({ data }) => ({
                method: 'POST',
                url: '/create',
                body: data
            })
        }),
        sendFiles: builder.mutation<any, { files: File[]; requestId: string }>({
            query: ({ files, requestId }) => {
                const formData = new FormData()
                for (let f of files) {
                    formData.append('files', f)
                }
                return {
                    method: 'POST',
                    url: `/uploadFiles/${requestId}`,
                    body: formData
                }
            }
        })
    })
})

export const { useGetRequestQuery, useCreateRequestMutation, useEditRequestMutation, useGetDictionariesQuery, useSendFilesMutation } =
    esmService
