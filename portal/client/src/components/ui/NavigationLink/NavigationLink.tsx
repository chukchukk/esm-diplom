import React from 'react'
import { Link } from 'react-router-dom'
import cn from 'classnames'
import styles from './NavigationLink.module.scss'

interface NavigationLinkProps {
    to: string
    label: string
    className?: string
}

function NavigationLink({ to, className, label }: NavigationLinkProps) {
    return (
        <Link to={to} className={cn(styles.self, className)}>
            {label}
        </Link>
    )
}

export default React.memo(NavigationLink)
