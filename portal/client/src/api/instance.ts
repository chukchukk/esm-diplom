import axios, { AxiosError, AxiosRequestConfig } from 'axios'
import { keycloak, KEYCLOAK_MIN_VALIDITY } from '../keycloak'
import { FetchBaseQueryArgs } from '@reduxjs/toolkit/dist/query/fetchBaseQuery'
import { BaseQueryFn } from '@reduxjs/toolkit/query'
import { PortalServiceEndpointName } from '../store/portalService'

const __AJAX_TIMEOUT__ = 900000
export const __API__ = '/api/v1/'

const waitBlobs: PortalServiceEndpointName[] = ['downloadFile']

export function createAxiosInstance() {
    const instance = axios.create({
        timeout: __AJAX_TIMEOUT__,
        responseType: 'json',
        headers: {}
    })

    instance.interceptors.request.use(tokenInterceptor, () => Promise.reject())

    return instance
}

function tokenInterceptor(rqConfig: AxiosRequestConfig) {
    return keycloak.updateToken(KEYCLOAK_MIN_VALIDITY).then(() => {
        return {
            ...rqConfig,
            headers: {
                ...rqConfig.headers,
                Authorization: `Bearer ${keycloak.token}`
            }
        }
    })
}

export const axiosInstance = createAxiosInstance()

export const axiosBaseQuery =
    ({
        baseUrl,
        prepareHeaders,
        method
    }: FetchBaseQueryArgs): BaseQueryFn<
        | {
              url?: string
              method?: AxiosRequestConfig['method']
              body?: AxiosRequestConfig['data']
          }
        | string
    > =>
    async (args, { getState, type, endpoint, extra }) => {
        let url = ''
        let method: AxiosRequestConfig['method'] = 'get'
        let data
        if (args && typeof args !== 'string') {
            url = args.url!
            method = args.method ?? 'get'
            data = args.body
        } else {
            url = args
        }
        let headers = {}
        if (prepareHeaders) {
            headers = await prepareHeaders(new Headers({}), { getState, type, endpoint, extra })
            headers = (headers as any).map
        }
        try {
            const config: AxiosRequestConfig = {
                url: `${baseUrl}${url}`,
                method,
                data,
                headers
            }
            let addBlob = waitBlobs.some(i => {
                const searchString = url.substring(url.lastIndexOf('/') + 1)
                if (searchString.includes('?')) {
                    return i === searchString.substring(0, searchString.indexOf('?'))
                }
                return searchString === i
            })
            if (addBlob && method.toLowerCase() === 'get') {
                config.responseType = 'blob'
            }
            const result = await axiosInstance(config)
            if (result.headers['content-disposition']) {
                return {
                    data: {
                        file: result.data,
                        fileName: result.headers['content-disposition'].substring(
                            result.headers['content-disposition'].lastIndexOf("'") + 1
                        )
                    }
                }
            }
            return { data: result.data }
        } catch (e) {
            let err = e as AxiosError
            console.error(err.toJSON())
            return {
                error: { status: err.response?.status, data: err.response?.data }
            }
        }
    }
