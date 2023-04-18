import { Operation, OperationGroup, OperationPreInvokeType, OperationPostInvokeType } from '@tesler-ui/core/interfaces/operation'
import { WidgetOperations } from '@tesler-ui/core/interfaces/widget'

export interface OperationPreInvokeCustom {
    type: OperationPreInvokeTypeCustom | OperationPreInvokeType
    message: string
    bc?: string
    buttonOkText?: string
}

export enum OperationPreInvokeTypeCustom {
    /**
     * Перед операцией пользователя будет показано всплывающее сообщение "Да/Нет",
     * и операция произойдет только если пользователь скажет "Да"
     */
    confirm = 'confirm',
    /**
     * Перед операцией пользователя будет показано всплывающее сообщение
     * с иконкой информации
     */
    info = 'info',
    /**
     * Перед операцией пользователя будет показано всплывающее сообщение
     * с иконкой ошибки и операция не будет выполнена
     */
    error = 'error',
    /**
     * Перед операцией пользователя будет показано всплывающая БК
     * и операция произойдет только если пользователь скажет "Да"
     */
    bc = 'bc'
}

/**
 * Тип действия, которое будет выполнено после операции пользователя
 */
export enum OperationPostInvokeTypeCustom {
    /**
     * Показать попап
     */
    showViewPopup = 'showViewPopup',
    /**
     * Сделать обновление нескольких бк
     */
    refreshMultipleBc = 'refreshMultipleBc'
}

export interface OperationPostInvokeCustom {
    type: OperationPostInvokeTypeCustom | OperationPostInvokeType
}

export interface OperationPostInvokeShowViewPopup extends OperationPostInvokeCustom {
    bcName?: string
    implementer?: string
    implementerId?: string
    widgetName?: string
}

export interface OperationPostInvokeRefreshMultipleBc extends OperationPostInvokeCustom {
    bc: string
    bcList: string
}

/**
 * Операция пользователя - CRUD или любое бизнес-действие.
 * Приходит в мете записи.
 *
 * @param bcKey - ключ БК операции
 */
export interface OperationCustom extends Operation {
    bcKey?: string
}
/**
 * Описание операций в опциях меты виджета, через который можно настраивать их доступность
 *
 * @param include Список включаемых операций или групп операций
 * @param exclude Список исключаемых операций или групп операций
 * @param ignoreScope TODO: описание
 */
export interface CustomWidgetOperations extends WidgetOperations {
    ignoreScope?: string[]
}

/**
 * Группа действий, показывает название группы и раскрываемые список ее действий,
 * а также несколько действий рядом с группой, которые видны не раскрывая список.
 */
export interface OperationGroupCustom extends OperationGroup {
    actions: OperationCustom[]
}
