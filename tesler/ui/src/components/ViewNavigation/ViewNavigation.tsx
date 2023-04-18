import React from 'react'
import { historyObj, useViewTabs } from '@tesler-ui/core'
import { Tabs } from 'antd'
import styles from './ViewNavigation.module.css'

export function ViewNavigation() {
    const tabs = useViewTabs(1)
    const handleChange = (key: string) => {
        historyObj.push(key)
    }

    return (
        <nav className={styles.container}>
            <Tabs activeKey={tabs?.find(item => item.selected)?.url} tabBarGutter={24} size="large" onChange={handleChange}>
                {tabs
                    .filter(item => item.url && !item.hidden)
                    .map(item => (
                        <Tabs.TabPane key={item.url} tab={<span className={styles.item}>{item.title}</span>} />
                    ))}
            </Tabs>
        </nav>
    )
}

export default React.memo(ViewNavigation)
