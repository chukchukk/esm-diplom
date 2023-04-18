import { createApi } from '@reduxjs/toolkit/query/react'
import { axiosBaseQuery } from '../api/instance'

export const portalService = createApi({
    reducerPath: 'portalService',
    baseQuery: axiosBaseQuery({ baseUrl: '/api/v1' }),
    endpoints: builder => ({
        downloadFile: builder.query<{ file: File; fileName: string }, { fileId: string; preview?: boolean }>({
            query: ({ fileId, preview }) => ({
                url: `/downloadFile?id=${fileId}&preview=${preview || false}`
            })
        })
    })
})

export const { useLazyDownloadFileQuery } = portalService

export type PortalServiceEndpointName = keyof typeof portalService.endpoints
