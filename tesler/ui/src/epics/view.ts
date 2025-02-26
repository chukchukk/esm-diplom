import { CustomEpic, actionTypes } from '../interfaces/actions'
import { Observable } from 'rxjs/Observable'
import { $do } from '../actions/types'
import { buildBcUrl, getFilters } from '@tesler-ui/core'
import { fetchBcCount } from '../api/bcCount'
import { EMPTY_ARRAY } from '../constants/constants'

const bcFetchCountEpic: CustomEpic = (action$, store) =>
    action$
        .ofType(actionTypes.bcFetchDataSuccess)
        .mergeMap(action => {
            const state = store.getState()
            const sourceWidget = state.view.widgets?.find(i => i.bcName === action.payload.bcName)

            if (!sourceWidget) {
                return Observable.empty()
            }

            const bcName = sourceWidget.bcName
            const filters = getFilters(state.screen.filters[bcName] || EMPTY_ARRAY)
            const bcUrl = buildBcUrl(bcName)
            return fetchBcCount(bcUrl, filters).mergeMap(({ data }) =>
                Observable.of(
                    $do.setBcCount({
                        bcName,
                        count: data
                    })
                )
            )
        })
        .catch(error => {
            console.error(error)
            return Observable.empty<never>()
        })

const closeViewPopupAfterSuccess: CustomEpic = (action$, store) =>
    action$.ofType(actionTypes.sendOperationSuccess).map(action => {
        const viewState = store.getState().view
        const { bcName } = action.payload
        const showed = viewState.popupData?.bcName === bcName

        if (showed) {
            return $do.closeViewPopup({ bcName })
        }
        return $do.emptyAction(null)
    })

export const viewEpics = {
    bcFetchCountEpic,
    closeViewPopupAfterSuccess
}
