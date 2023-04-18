import { actionTypes, AnyAction, CustomEpic } from '../interfaces/actions'
import { $do } from '../actions/types'
import { Observable } from 'rxjs/Observable'
import { RouteType } from '@tesler-ui/core/interfaces/router'
import { ActionPayloadTypes as TeslerActionPayloadTypes } from '@tesler-ui/core'
import { EMPTY_ARRAY } from '../constants/constants'
import { notification } from 'antd'

const changeLocationUsingRole: CustomEpic = (action$, store) =>
    action$
        .ofType(actionTypes.changeLocation)
        .filter(action => !!action.payload.location?.params.role)
        .mergeMap(action => {
            const { location } = action.payload

            const newPayload = {
                ...action.payload,
                location: {
                    ...action.payload.location,
                    params: {}
                }
            }
            const state = store.getState()
            const roles = state.session.roles?.map(i => i.key) || EMPTY_ARRAY
            const requestedRoles = location!.params.role as any
            if (!roles.some(v => requestedRoles.includes(v))) {
                notification.error({ message: 'У тебя нет необходимой роли:', description: requestedRoles.join(', '), duration: 15 })
                return Observable.empty()
            }

            return Observable.concat(
                !requestedRoles.includes(state.session.activeRole)
                    ? Observable.of($do.switchRole({ role: requestedRoles[0] }))
                    : Observable.empty(),
                Observable.of($do.changeLocation(newPayload as TeslerActionPayloadTypes['changeLocation']))
            )
        })

const customChangeLocation: CustomEpic = (action$, store) =>
    action$
        .ofType(actionTypes.changeLocation)
        .filter(action => !!action.payload.location?.params && Object.keys(action.payload.location.params).length === 0)
        .mergeMap(() => {
            const state = store.getState()

            // User not logged
            if (!state.session.active) {
                return Observable.empty()
            }

            if (state.router.type === RouteType.router) {
                return Observable.of($do.handleRouter(state.router))
            }

            // Reload screen if nextScreen and currentScreen not equal
            // With the default route type use the first default screen, if not exist then first screen
            const currentScreenName = state.screen.screenName
            const defaultScreenName = state.session.screens.find(screen => screen.defaultScreen)?.name || state.session.screens[0]?.name
            const nextScreenName = state.router.type === RouteType.default ? defaultScreenName : state.router.screenName

            if (nextScreenName !== currentScreenName) {
                const nextScreen = state.session.screens.find(item => item.name === nextScreenName)
                return nextScreen
                    ? Observable.of($do.selectScreen({ screen: nextScreen }))
                    : Observable.of($do.selectScreenFail({ screenName: nextScreenName! }))
            }
            // Check cursor different between store and url
            const currentViewName = state.view.name
            const nextViewName = state.router.viewName
            const nextCursors = parseBcCursors(state.router.bcPath) || {}
            const cursorsDiffMap: Record<string, string> = {}
            Object.entries(nextCursors).forEach(entry => {
                const [bcName, cursor] = entry
                const bc = state.screen.bo.bc[bcName]
                if (!bc || bc?.cursor !== cursor) {
                    cursorsDiffMap[bcName] = cursor
                }
            })
            const needUpdateCursors = Object.keys(cursorsDiffMap).length
            const needUpdateViews = nextViewName !== currentViewName
            const resultObservables: Array<Observable<AnyAction>> = []
            // if cursors have difference, then put new cursors and mark BC as "dirty"
            if (needUpdateCursors) {
                resultObservables.push(Observable.of($do.bcChangeCursors({ cursorsMap: cursorsDiffMap })))
            }
            // reload view if not equ
            if (needUpdateViews) {
                const nextView = nextViewName
                    ? state.screen.views.find(item => item.name === nextViewName)
                    : state.screen.primaryView
                    ? state.screen.views.find(item => item.name === state.screen.primaryView)
                    : state.screen.views[0]
                resultObservables.push(
                    nextView ? Observable.of($do.selectView(nextView)) : Observable.of($do.selectViewFail({ viewName: nextViewName! }))
                )
            }
            // If CURSOR has been updated but VIEW hasn`t changed, need update DATA
            if (needUpdateCursors && !needUpdateViews) {
                Object.entries(nextCursors).forEach(entry => {
                    const [bcName, cursor] = entry
                    if (!state.data[bcName].find(item => item.id === cursor)) {
                        resultObservables.push(Observable.of($do.bcForceUpdate({ bcName })))
                    }
                })
            }
            // The order is important (cursors are placed first, then the view is reloaded)
            return Observable.concat(...resultObservables)
        })

const customLoginDone: CustomEpic = (action$, store) =>
    action$.ofType(actionTypes.loginDone).switchMap(() => {
        const state = store.getState()

        if (state.router.type === RouteType.router) {
            return Observable.of($do.handleRouter(state.router))
        }

        if (state.router.params.role) {
            return Observable.of(
                $do.changeLocation({
                    location: state.router,
                    action: 'PUSH'
                })
            )
        }

        const nextScreenName = state.router.screenName
        const nextScreen =
            state.session.screens.find(item => (nextScreenName ? item.name === nextScreenName : item.defaultScreen)) ||
            state.session.screens[0]
        return nextScreen
            ? Observable.of<AnyAction>($do.selectScreen({ screen: nextScreen }))
            : Observable.of<AnyAction>($do.selectScreenFail({ screenName: nextScreenName! }))
    })

export const routerEpics = {
    changeLocationUsingRole,
    changeLocation: customChangeLocation,
    loginDone: customLoginDone
}

function parseBcCursors(bcPath?: string) {
    if (!bcPath) {
        return null
    }
    const cursors: Record<string, string> = {}
    const tokens = bcPath.split('/')
    for (let i = 0; i < tokens.length; i = i + 2) {
        if (tokens[i + 1]) {
            cursors[tokens[i]] = tokens[i + 1]
        }
    }
    return cursors
}
