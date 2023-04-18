import React from 'react'
import ViewNavigation from '../ViewNavigation/ViewNavigation'
import UserMenu from './components/UserMenu/UserMenu'
import styles from './AppBar.module.css'
import { useSelector } from 'react-redux'
import { AppState } from '../../interfaces/storeSlices'
import { Layout } from 'antd'
import cn from 'classnames'
import { WidgetTypes } from '@tesler-ui/core/interfaces/widget'

function AppBar() {
    const widgets = useSelector((state: AppState) => state.view.widgets)
    const navigation = widgets?.find(i => i.type === WidgetTypes.ViewNavigation)
    return (
        <Layout.Header className={cn(styles.container, { [styles.withTabs]: navigation })}>
            {navigation && <ViewNavigation />}
            <UserMenu />
        </Layout.Header>
    )
}

export default React.memo(AppBar)
