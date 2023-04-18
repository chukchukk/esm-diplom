import { AnyAction, Dispatch, Middleware, MiddlewareAPI } from '@reduxjs/toolkit'
import { RootState } from '../store'
import { esmService } from '../esmService'
import { setRequestCategory, setRequestType } from '../reducers/request'

const initRequestTypeMiddleware: Middleware =
    ({ getState, dispatch }: MiddlewareAPI<Dispatch<AnyAction>, RootState>) =>
    (next: Dispatch<AnyAction>) =>
    (action: AnyAction) => {
        if (esmService.endpoints.getRequest.matchFulfilled(action)) {
            dispatch(setRequestCategory(action.payload.type.category))
            dispatch(setRequestType(action.payload.type.key))
        }
        return next(action)
    }

export default initRequestTypeMiddleware
