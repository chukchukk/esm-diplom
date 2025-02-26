import { actionTypes, AnyAction } from '../interfaces/actions'
import { AppState } from '../interfaces/storeSlices'
import { Session } from '@tesler-ui/core/interfaces/session'

export type CustomSession = Session & { logout: boolean }

/**
 * Your initial state for this slice
 */
export const initialState: CustomSession = {
    active: false,
    screens: [],
    loginSpin: false,
    logout: false
}

export default function sessionReducer(state: CustomSession = initialState, action: AnyAction, store?: Readonly<AppState>): CustomSession {
    switch (action.type) {
        /**
         * Your reducers for this slice
         */
        case actionTypes.logout: {
            return {
                ...state,
                loginSpin: false,
                active: false,
                logout: true
            }
        }
        case actionTypes.loginDone: {
            return {
                ...state,
                active: true,
                logout: false
            }
        }
        case actionTypes.logoutDone: {
            return {
                ...state,
                active: false
            }
        }
        default:
            return state
    }
}
