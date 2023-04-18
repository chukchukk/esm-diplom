import { createContext } from 'react'
import { PageLocalDispatch, PageLocalStore } from '../../interfaces/form'

export const LocalStateContext = createContext<{ localState?: PageLocalStore; localDispatch?: PageLocalDispatch }>({})
