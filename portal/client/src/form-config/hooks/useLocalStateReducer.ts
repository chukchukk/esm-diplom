import { useReducer } from 'react'
import { initState, reducer } from '../local-state'

function useLocalStateReducer() {
    return useReducer(reducer, initState)
}

export default useLocalStateReducer
