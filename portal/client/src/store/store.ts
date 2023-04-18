import { combineReducers, configureStore } from '@reduxjs/toolkit'
import { esmService } from './esmService'
import { request } from './reducers/request'
import initRequestTypeMiddleware from './middlewares/initRequestTypeMiddleware'
import { portalService } from './portalService'

const rootReducer = combineReducers({
    [request.name]: request.reducer,
    [portalService.reducerPath]: portalService.reducer,
    [esmService.reducerPath]: esmService.reducer
})

export const setupStore = () => {
    return configureStore({
        reducer: rootReducer,
        middleware: getDefaultMiddleware =>
            getDefaultMiddleware({ serializableCheck: false }).concat(
                esmService.middleware,
                initRequestTypeMiddleware,
                portalService.middleware
            )
    })
}

export type RootState = ReturnType<typeof rootReducer>
export type AppStore = ReturnType<typeof setupStore>
export type AppDispatch = AppStore['dispatch']
