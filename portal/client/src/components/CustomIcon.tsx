import React from 'react'
import customIcon from '../assets/icons'

type IconType = keyof typeof customIcon

interface CustomIconProps {
    type: IconType | string
    className?: string
    width?: number
    height?: number
}
const CustomIcon: React.FunctionComponent<CustomIconProps> = props => {
    const { type, className } = props
    return (
        <img
            width={props.width || undefined}
            height={props.height || undefined}
            alt={type}
            className={className}
            src={customIcon[type as IconType].default}
        />
    )
}

export default React.memo(CustomIcon)
