import React from 'react'
import './imports/rxjs'
import { render } from 'react-dom'
import { Provider } from '@tesler-ui/core'
import { ConfigProvider } from 'antd'
import ruRu from 'antd/es/locale/ru_RU'
import { reducers } from './reducers'
import { epics } from './epics'
import './index.css'
import AppLayout from './components/AppLayout/AppLayout'
import { axiosInstance } from './api/session'
import { CoreMiddlewares, CustomMiddleware, CustomMiddlewares } from '@tesler-ui/core/interfaces/customMiddlewares'
import { savePickListMiddleware } from './middlewares/savePicklistMiddleware'

const middlewares: CustomMiddlewares<Partial<CoreMiddlewares> & { savePickListMiddleware: CustomMiddleware }> = {
    savePickListMiddleware: {
        implementation: savePickListMiddleware,
        priority: 'BEFORE'
    }
}

const App = (
    <Provider customReducers={reducers} customEpics={epics} axiosInstance={axiosInstance()} customMiddlewares={middlewares} lang={'ru'}>
        <ConfigProvider locale={ruRu}>
            <AppLayout />
        </ConfigProvider>
    </Provider>
)

render(App, document.getElementById('root'))
