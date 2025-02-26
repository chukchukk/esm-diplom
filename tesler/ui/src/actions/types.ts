/**
 * This is an utility class for typing payload of redux actions
 */
import { ActionPayloadTypes as TeslerActionPayloadTypes, createActionCreators } from '@tesler-ui/core'

const z = null as any

export const SSO_AUTH = 'SSO_AUTH'

/**
 * Declare your redux actions here with action name and payload type
 *
 * Assign every action an empty value (`z`) to prevent Typescript from erasing it in runtime
 *
 * @see https://github.com/microsoft/TypeScript/issues/12437
 */
export class CustomActionTypes extends TeslerActionPayloadTypes {
    /**
     * Set state of side menu: open or collapsed
     */
    changeMenuCollapsed: boolean = z

    /**
     * An example of action and payload declaration
     */
    customAction: {
        customMessage: string
    } = z

    /**
     * You can expand payload of internal tesler-ui actions:
     */
    changeLocation: TeslerActionPayloadTypes['changeLocation'] & {
        customPayloadField?: number
    } = z

    /**
     * Set the number of records for BC
     */
    setBcCount: {
        count: number
        bcName: string
    } = z
    /**
     * Set the number of records for BC
     */
    forceChangeWidgetCursor: {
        cursor: string
        bcName: string
    } = z
    setKeepBcPage: {
        page: number
    } = z
    forceChangeBcPage: {
        bcName: string
        page: number
    } = z
}

/**
 * Action creator helper allowing to create action typed actions:
 *
 * $do.customAction({ customMessage: 'test '}) will result in:
 * { type: 'customAction', payload: { customMessage: 'test' } }
 */
export const $do = createActionCreators(new CustomActionTypes())
