/*
 * TESLERDOC - UI
 * Copyright (C) 2020 Tesler Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react'
import { Tabs } from 'antd'
import { historyObj, useViewTabs } from '@tesler-ui/core'
import styles from './ThirdLevelViewNavigation.module.css'

export function ThirdLevelViewNavigation() {
    const tabs = useViewTabs(2)
    const handleChange = (key: string) => {
        historyObj.push(key)
    }

    return (
        <div className={styles.container}>
            <Tabs activeKey={tabs?.find(item => item.selected)?.url} tabBarGutter={24} size="large" onChange={handleChange}>
                {tabs.map(item => (
                    <Tabs.TabPane key={item.url} tab={<span className={styles.item}>{item.title}</span>} />
                ))}
            </Tabs>
        </div>
    )
}

export default React.memo(ThirdLevelViewNavigation)
