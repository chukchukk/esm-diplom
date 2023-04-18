import { LocalAction, PageLocalStore } from '../interfaces/form'

export const initState: PageLocalStore = {
    files: {}
}

export function reducer(state: typeof initState, action: LocalAction) {
    switch (action.type) {
        case 'clearFieldFiles': {
            const { fieldName } = action.payload as { fieldName: string }
            const newFiles = { ...state.files }
            delete newFiles[fieldName]
            return {
                ...state,
                files: newFiles
            }
        }
        case 'addFiles': {
            const { files, fieldName } = action.payload as { fieldName: string; files: File[] }
            if (!state.files[fieldName]) {
                return {
                    ...state,
                    files: {
                        ...state.files,
                        [fieldName]: files
                    }
                }
            } else {
                return {
                    ...state,
                    files: {
                        ...state.files,
                        [fieldName]: [...state.files[fieldName], ...files]
                    }
                }
            }
        }
        case 'removeFile': {
            const { fieldName, fileIndex } = action.payload as { fieldName: string; fileIndex: number }
            const newFiles = state.files[fieldName].slice()
            newFiles.splice(fileIndex, 1)
            return {
                ...state,
                files: {
                    ...state.files,
                    [fieldName]: newFiles
                }
            }
        }
        default: {
            return state
        }
    }
}
