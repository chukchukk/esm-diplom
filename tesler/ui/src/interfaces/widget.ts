import {
    WidgetMeta,
    WidgetTypes,
    WidgetOptions,
    WidgetFieldsOrBlocks,
    WidgetFormField,
    PopupWidgetTypes
} from '@tesler-ui/core/interfaces/widget'

export enum CustomFieldTypes {
    MultipleSelect = 'multipleSelect'
}

export enum CustomWidgetTypes {
    Steps = 'Steps',
    Funnel = 'Funnel',
    RingProgress = 'RingProgress',
    DashboardList = 'DashboardList',
    FormPopup = 'FormPopup',
    InnerTabs = 'InnerTabs',
    ThirdLevelInnerTabs = 'ThirdLevelInnerTabs'
}

export const extendedPopupWidgetTypes: string[] = [...PopupWidgetTypes, 'FormPopup']

export const removeRecordOperationWidgets: Array<WidgetTypes | string> = [WidgetTypes.List]

export interface StepsWidgetMeta extends WidgetMeta {
    type: CustomWidgetTypes.Steps
    options: WidgetOptions & {
        stepsOptions: {
            stepsDictionaryKey: string
        }
    }
}

export interface FunnelWidgetMeta extends WidgetMeta {
    type: CustomWidgetTypes.Funnel
    options: WidgetOptions & { funnelOptions: { dataKey: string } }
}

export interface RingProgressWidgetMeta extends WidgetMeta {
    type: CustomWidgetTypes.RingProgress
    options: WidgetOptions & { ringProgressOptions: { text: string; numberField: string; descriptionField: string; percentField: string } }
}

export interface WidgetFormPopupMeta extends WidgetMeta {
    type: CustomWidgetTypes.FormPopup
    fields: WidgetFieldsOrBlocks<WidgetFormField>
}
