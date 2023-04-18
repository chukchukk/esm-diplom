import { $do, processPostInvokeImpl } from '@tesler-ui/core'
import { Observable } from 'rxjs'
import { CustomEpic, actionTypes } from '../interfaces/actions'
import {
    OperationPreInvokeCustom,
    OperationPreInvokeTypeCustom,
    OperationPostInvokeShowViewPopup,
    OperationPostInvokeTypeCustom,
    OperationPostInvokeRefreshMultipleBc
} from '../interfaces/operation'
import { OperationPostInvokeType } from '@tesler-ui/core/interfaces/operation'
import { $do as $customDo } from '../actions/types'

const processPreInvokeConfirm: CustomEpic = (action$, store) =>
    action$.ofType(actionTypes.processPreInvoke).mergeMap(action => {
        const { bcName, operationType } = action.payload
        const confirm = action.payload.preInvoke as OperationPreInvokeCustom
        switch (confirm.type) {
            case OperationPreInvokeTypeCustom.bc: {
                return Observable.of(
                    $do.showViewPopup({
                        bcName: confirm.bc || '',
                        calleeBCName: bcName,
                        assocValueKey: operationType
                    })
                )
            }
            default:
                return Observable.empty()
        }
    })

const processPostInvoke: CustomEpic = (action$, store) =>
    action$.ofType(actionTypes.processPostInvoke).mergeMap(action => {
        switch (action.payload.postInvoke.type as OperationPostInvokeTypeCustom | OperationPostInvokeType) {
            case OperationPostInvokeTypeCustom.showViewPopup: {
                const postInvoke = action.payload.postInvoke as OperationPostInvokeShowViewPopup
                const { bcName = '', widgetName = '', implementer = '', implementerId = '' } = postInvoke
                return Observable.concat(
                    Observable.of($do.showViewPopup({ bcName, widgetName })),
                    Observable.of(
                        $do.viewPutPickMap({
                            map: { implementer, implementerId },
                            bcName
                        })
                    )
                )
            }
            case OperationPostInvokeTypeCustom.refreshMultipleBc: {
                const postInvoke = action.payload.postInvoke as OperationPostInvokeRefreshMultipleBc
                const bcNameList = postInvoke.bcList.split(';')
                const observables = bcNameList.map(bcName => {
                    return Observable.of(
                        $do.processPostInvoke({
                            bcName: action.payload.bcName,
                            widgetName: action.payload.widgetName,
                            postInvoke: {
                                bc: bcName,
                                type: 'refreshBC'
                            }
                        })
                    )
                })
                return Observable.merge(...observables)
            }
            default: {
                return processPostInvokeImpl(action, store) as unknown as Observable<any>
            }
        }
    })
const keepBcPage: CustomEpic = (action$, store) =>
    action$.ofType(actionTypes.bcRemoveAllFilters).mergeMap(action => {
        const { bcName } = action.payload
        const keepPage = store.getState().screen.keepBcPage
        if (keepPage) {
            return Observable.of($customDo.forceChangeBcPage({ bcName, page: keepPage }))
        }
        return Observable.empty()
    })
export const screenEpics = {
    processPreInvokeConfirm,
    processPostInvoke,
    keepBcPage
}
