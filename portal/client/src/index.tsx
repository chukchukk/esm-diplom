import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import './styles/globals.scss'
import './styles/common.scss'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import { keycloak, keycloakOptions } from './keycloak'
import { setupStore } from './store/store'
import { Provider } from 'react-redux'

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement)
export const store = setupStore()

root.render(
    <Provider store={store}>
        <ReactKeycloakProvider authClient={keycloak} initOptions={keycloakOptions}>
            <App />
        </ReactKeycloakProvider>
    </Provider>
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
