import { coreActions, $do } from '@tesler-ui/core'
import { OperationTypeCrud } from '@tesler-ui/core/interfaces/operation'
import { AnyAction, Dispatch, MiddlewareAPI } from 'redux'
import { AppState } from '../interfaces/storeSlices'

export const savePickListMiddleware =
    ({ getState, dispatch }: MiddlewareAPI<Dispatch<AnyAction>, AppState>) =>
    (next: Dispatch) =>
    (action: any) => {
        if (action.type === coreActions.viewClearPickMap) {
            const pickListBC = getState().view?.popupData?.bcName
            const bcName = pickListBC && getState().screen.bo.bc[pickListBC]?.parentName
            const widgetName = getState().view.widgets.find(
                widget => (widget.type === 'List' || widget.type === 'Info') && widget.bcName === bcName
            )?.name
            if (pickListBC && bcName && widgetName) {
                dispatch(
                    $do.sendOperation({
                        bcName,
                        operationType: OperationTypeCrud.save,
                        widgetName
                    })
                )
            }
        }
        return next(action)
    }
