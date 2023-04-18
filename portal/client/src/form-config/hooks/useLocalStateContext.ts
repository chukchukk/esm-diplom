import { useContext } from 'react'
import { LocalStateContext } from '../context/local-state-context'

function useLocalStateContext() {
    return useContext(LocalStateContext)
}

export default useLocalStateContext
