import { AnyAction, actionTypes } from '../interfaces/actions'
import { AppState, ScreenState } from '../interfaces/storeSlices'

export type CustomScreen = ScreenState & {
    keepBcPage: number | null
}

/**
 * Your initial state for this slice
 */
export const initialState: CustomScreen = {
    menuCollapsed: false,
    screenName: '',
    bo: {
        activeBcName: '',
        bc: {}
    },
    views: [],
    primaryView: '',
    cachedBc: {},
    filters: {},
    sorters: {},
    keepBcPage: null
}

export default function screenReducer(state: CustomScreen = initialState, action: AnyAction, store?: Readonly<AppState>): CustomScreen {
    switch (action.type) {
        case actionTypes.changeMenuCollapsed: {
            return {
                ...state,
                menuCollapsed: action.payload
            }
        }
        /**
         * Your reducers for this slice
         */

        /**
         * An example reducer for custom action
         */
        case actionTypes.customAction: {
            return state
        }
        case actionTypes.forceChangeWidgetCursor: {
            const bobcs = state.bo.bc
            const newBobcs: any = { ...bobcs, [action.payload.bcName]: { ...bobcs[action.payload.bcName], cursor: action.payload.cursor } }
            return { ...state, bo: { ...state.bo, bc: newBobcs } }
        }
        case actionTypes.forceChangeBcPage: {
            return {
                ...state,
                bo: {
                    ...state.bo,
                    bc: {
                        ...state.bo.bc,
                        [action.payload.bcName]: {
                            ...state.bo.bc[action.payload.bcName],
                            page: action.payload.page
                        }
                    }
                },
                keepBcPage: null
            }
        }
        case actionTypes.setKeepBcPage: {
            const { page } = action.payload

            return {
                ...state,
                keepBcPage: page
            }
        }
        default:
            return state
    }
}
