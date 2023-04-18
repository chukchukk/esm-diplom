import { useKeycloak } from '@react-keycloak/web'
import React from 'react'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { NavBar } from './components/NavBar/NavBar'
import { MyRequests } from './pages/myRequests/MyRequests'
import { RequestCard } from './pages/requestCard/RequestCard'
import { NotFound } from './pages/notFound/NotFound'
import CreateEditRequestPage from './pages/CreateEditRequestPage/CreateEditRequestPage'
import { RequestCategory } from './interfaces/request'

function App() {
    const { keycloak } = useKeycloak()

    const isLoggedIn = keycloak.authenticated

    if (!isLoggedIn) {
        return <div>unauthorized</div>
    }

    return (
        <div className="App">
            <BrowserRouter>
                <NavBar />
                <Routes>
                    <Route path="/" element={<Navigate to="/my-requests" />} />
                    <Route path="/my-requests" element={<MyRequests />} />
                    <Route path="/my-requests/:requestId" element={<RequestCard />} />
                    <Route
                        path="/personal-data-change"
                        element={
                            <CreateEditRequestPage
                                category={RequestCategory.PERSONAL_DATA_CHANGE}
                                pageTitle="Изменение персональных данных"
                            />
                        }
                    />
                    <Route
                        path="/personal-data-change/:requestId"
                        element={<CreateEditRequestPage category={RequestCategory.PERSONAL_DATA_CHANGE} pageTitle="Заявка" />}
                    />

                    <Route
                        path="/document"
                        element={
                            <CreateEditRequestPage category={RequestCategory.DOCUMENT} pageTitle="Получение справок и копий документов" />
                        }
                    />
                    <Route
                        path="/document/:requestId"
                        element={<CreateEditRequestPage category={RequestCategory.DOCUMENT} pageTitle="Заявка" />}
                    />
                    <Route path="/notFound" element={<NotFound />} />
                    <Route path="*" element={<Navigate to="/notFound" replace />} />
                </Routes>
            </BrowserRouter>
        </div>
    )
}

export default App
