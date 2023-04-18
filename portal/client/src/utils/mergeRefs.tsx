import React from 'react'
/**
 * Let's say 'Thanks' to this guy https://github.com/gregberge/react-merge-refs/blob/main/src/index.tsx
 * @param refs
 */
export function mergeRefs<T = any>(refs: Array<React.MutableRefObject<T> | React.LegacyRef<T>>): React.RefCallback<T> {
    return value => {
        refs.forEach(ref => {
            if (typeof ref === 'function') {
                ref(value)
            } else if (ref != null) {
                ;(ref as React.MutableRefObject<T | null>).current = value
            }
        })
    }
}
