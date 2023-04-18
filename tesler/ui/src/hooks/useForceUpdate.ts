import { useCallback, useReducer } from 'react'

export function useForceUpdate() {
    const [, forceUpdate] = useReducer(x => x + 1, 0)
    return useCallback(() => {
        forceUpdate()
    }, [])
}
