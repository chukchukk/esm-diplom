import { FieldTypes, RequestCategoryType } from './request'

export interface IMyReq {
    requestId: number
    createdDate: string
    legalEntityName: string
    requestType: {
        value: string
        isInternal: boolean
        category: string
    }
    status: string
}

interface IHistoryItem {
    actionType: string
    actionAuthor: string
    color: string | null
    comment: string
    createdDate: string
    errors: any
    id: string
    status: string
    vstamp: number
}
interface IHistoryItemNew {
    id: string
    errors: any
    vstamp: number
    actionType: string
    actionAuthor: string
    actionAuthorId: number

    status: string
    comment: string
    createdDate: string
    color: string | null
}

export interface IFileItem {
    fileId: string
    fileName: string
}

export interface RequestFieldInfo {
    [fieldKey: string]: { type: FieldTypes; value: string }
}

interface IDataFieldItem {
    value: string | null
    type: string | null
    visible: boolean
    isUICardAction: boolean | null
}

export interface IReqCardInfo {
    hint: string | null
    requestCardButtons: string[]
    requestHeader: {
        createdDate: string
        legalEntityName: string
        status: string
        type: string
    }
    requestData: Record<string, any>
    requestDataFields: Record<string, IDataFieldItem>[]
    requestFiles: {
        employeeFiles: {
            files: IFileItem[]
            actions: string[]
        }
        implementerFiles: {
            files: IFileItem[]
            actions: string[]
        }
    }
    requestApprovalHistory: IHistoryItemNew[]
}

export type RequestData = { type: RequestTypeInfo } & Record<string, any>

export interface PresentRequestInfo {
    approvalHistory: IHistoryItem[]
    createdDate: string
    requestData: RequestData
    requestDataId: number
    status: string
}

export interface RequestTypeInfo {
    category: RequestCategoryType
    isInternal: boolean
    key: string
    value: string
}
