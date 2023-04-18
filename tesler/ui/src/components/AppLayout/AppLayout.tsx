import React from 'react'
import { Layout, Spin } from 'antd'
import AppSide from '../AppSide/AppSide'
import AppBar from '../AppBar/AppBar'
import { useDispatch, useSelector } from 'react-redux'
import { AppState } from '../../interfaces/storeSlices'
import DevPanel from '../DevPanel/DevPanel'
import { SSO_AUTH } from '../../actions/types'
import styles from './AppLayout.module.css'
import View from '../View/View'
import ModalInvoke from '../ModalInvoke/ModalInvoke'
import SystemNotifications from '../SystemNotifications/SystemNotifications'
import { CustomWidgetTypes } from '../../interfaces/widget'
import { FormPopupWidget } from '../widgets/FormPopup/FormPopupWidget'
import { WidgetFormField } from '@tesler-ui/core/interfaces/widget'

export const AppLayout: React.FC = () => {
    const sessionActive = useSelector((state: AppState) => state.session.active)
    const logoutRequested = useSelector((state: AppState) => state.session.logout)
    const modalInvoke = useSelector((state: AppState) => state.view.modalInvoke)
    const dispatch = useDispatch()
    const widgets = useSelector((state: AppState) => state.view.widgets)

    const popupWidgets = widgets.filter(widget => widget.type === CustomWidgetTypes.FormPopup)

    React.useEffect(() => {
        if (!sessionActive && !logoutRequested) {
            dispatch({ type: SSO_AUTH })
        }
    }, [sessionActive, logoutRequested, dispatch])

    return sessionActive ? (
        <Layout>
            <DevPanel />
            {modalInvoke?.operation && <ModalInvoke />}
            {popupWidgets &&
                popupWidgets.map((widget, index) => (
                    <FormPopupWidget
                        key={index}
                        meta={{
                            ...widget,
                            type: CustomWidgetTypes.FormPopup,
                            fields: widget.fields as WidgetFormField[]
                        }}
                    />
                ))}
            <SystemNotifications />
            <Layout className={styles.appLayout}>
                <AppSide />
                <Layout.Content>
                    <AppBar />
                    <View />
                </Layout.Content>
            </Layout>
        </Layout>
    ) : (
        <div className={styles.spinContainer}>
            <Spin size="large" />
        </div>
    )
}

export default React.memo(AppLayout)
