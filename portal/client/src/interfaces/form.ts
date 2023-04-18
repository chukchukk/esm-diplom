import { Dispatch, ReactNode } from 'react'

export interface PageLocalStore {
    files: Record<string, File[]>
}

export interface LocalAction {
    type: 'addFiles' | 'removeFile' | 'clearFieldFiles'
    payload: unknown
}

export type PageLocalDispatch = Dispatch<LocalAction>

export interface CommonFormProps {
    selectTypeComponent: ReactNode
    edit?: boolean
}

export type FormComponent = (props: CommonFormProps) => JSX.Element
