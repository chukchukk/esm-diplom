import React from 'react'
import { CustomWidgetDescriptor, WidgetTypes } from '@tesler-ui/core/interfaces/widget'
import Card from '../Card/Card'
import { View as TeslerView } from '@tesler-ui/core'
import { CustomFieldTypes, CustomWidgetTypes } from '../../interfaces/widget'
import MultipleSelectField from '../../fields/MultipleSelectField/MultipleSelectField'
import Form from '../widgets/Form/Form'
import Header from '../widgets/Header/Header'
import AssocListPopup from '../widgets/AssocListPopup/AssocListPopup'
import PickListPopup from '../widgets/PickListPopup/PickListPopup'
import EmptyCard from '../EmptyCard/EmptyCard'
import styles from './View.module.css'
import Info from '../widgets/Info/Info'
import Table from '../widgets/Table/Table'
import { FieldType } from '@tesler-ui/core/interfaces/view'
import Dictionary from '../../fields/Dictionary/Dictionary'
import Steps from '../widgets/Steps/Steps'
import { DashboardLayout } from '../ui/DashboardLayout/DashboardLayout'
import Funnel from '../widgets/Funnel/Funnel'
import RingProgress from '../widgets/RingProgress/RingProgress'
import DashboardCard from '../DashboardCard/DashboardCard'
import DashboardList from '../widgets/DashboardList/DashboardList'
import PickListField from '../../fields/PickListField/PickListField'
import { ViewNavigation } from '../ViewNavigation/ViewNavigation'
import { ThirdLevelViewNavigation } from '../ThirdLevelViewNavigation/ThirdLevelViewNavigation'
import { FormPopupWidget } from '../widgets/FormPopup/FormPopupWidget'

const skipWidgetTypes = [WidgetTypes.SecondLevelMenu, WidgetTypes.ViewNavigation]

const customFields = {
    [FieldType.dictionary]: Dictionary,
    [CustomFieldTypes.MultipleSelect]: MultipleSelectField,
    [FieldType.pickList]: PickListField
}

const customWidgets: Partial<Record<CustomWidgetTypes | WidgetTypes, CustomWidgetDescriptor>> = {
    [WidgetTypes.Form]: { component: Form },
    [WidgetTypes.Info]: { component: Info },
    [WidgetTypes.List]: { component: Table },
    [WidgetTypes.HeaderWidget]: { component: Header, card: EmptyCard },
    [CustomWidgetTypes.Steps]: { component: Steps, card: EmptyCard },
    [CustomWidgetTypes.Funnel]: { component: Funnel, card: DashboardCard },
    [CustomWidgetTypes.RingProgress]: { component: RingProgress, card: DashboardCard },
    [CustomWidgetTypes.DashboardList]: { component: DashboardList, card: DashboardCard },
    [WidgetTypes.AssocListPopup]: AssocListPopup,
    [CustomWidgetTypes.InnerTabs]: { component: ViewNavigation, card: EmptyCard },
    [CustomWidgetTypes.ThirdLevelInnerTabs]: { component: ThirdLevelViewNavigation, card: EmptyCard },
    [WidgetTypes.PickListPopup]: PickListPopup,
    [CustomWidgetTypes.FormPopup]: FormPopupWidget
}

function View() {
    return (
        <div className={styles.container}>
            <TeslerView
                customWidgets={customWidgets as Record<string, CustomWidgetDescriptor>}
                customFields={customFields}
                card={Card as any}
                skipWidgetTypes={skipWidgetTypes}
                customLayout={DashboardLayout}
            />
        </div>
    )
}

export default React.memo(View)
