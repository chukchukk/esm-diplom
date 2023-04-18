import { ScreenState as TeslerScreenState } from '@tesler-ui/core/interfaces/screen'
import { Store } from '@tesler-ui/core/interfaces/store'
import { DataState } from '@tesler-ui/core/interfaces/data'
import { CustomSession } from '../reducers/session'
import { CustomView } from '../reducers/view'
import { CustomScreen } from '../reducers/screen'

/**
 * You can change typings or add new store slices here
 */
export interface AppReducers extends Partial<Store> {
    screen: CustomScreen
    data: DataState
    view: CustomView
    session: CustomSession
}

export type AppState = Store & AppReducers

export interface ScreenState extends TeslerScreenState {
    menuCollapsed: boolean
}
