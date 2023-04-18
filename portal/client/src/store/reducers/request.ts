import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { RequestCategoryType, RequestSlice, RequestType } from '../../interfaces/request'

const initState: RequestSlice = {}

export const request = createSlice({
    name: 'request',
    initialState: initState,
    reducers: {
        clearRequestInfo: (state, action: PayloadAction<undefined>) => {
            return initState
        },
        setRequestCategory: (state, action: PayloadAction<RequestCategoryType>) => {
            state.category = action.payload
        },
        clearRequestCategory: (state, action) => {
            state.category = undefined
        },
        setRequestType: (state, action: PayloadAction<RequestType>) => {
            state.type = action.payload
        },
        clearRequestType: (state, action) => {
            state.type = undefined
        },
        setCreationLock: (state, action: PayloadAction<undefined | boolean>) => {
            state.creationLock = action.payload
        }
    }
})

export const { setRequestType, clearRequestType, setRequestCategory, clearRequestCategory, clearRequestInfo, setCreationLock } =
    request.actions
