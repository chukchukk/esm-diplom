import React from 'react'
import cn from 'classnames'
import styles from './PageContainer.module.scss'
interface PageContainerProps {
    children: React.ReactNode
    title: string
    linksComponent: React.ReactNode
    mainColumnClassName?: string
    secondaryColumnClassName?: string
}

function PageContainer({ children, title, linksComponent, mainColumnClassName, secondaryColumnClassName }: PageContainerProps) {
    return (
        <div className="sectionOuterGradient">
            <div className={cn('sectionInner', styles.requestContainer)}>
                <h2 className={styles.title}>{title}</h2>
                <div className={styles.contentWrapper}>
                    <div className={cn(styles.mainColumn, mainColumnClassName)}>{children}</div>
                    <div className={cn(styles.secondaryColumn, secondaryColumnClassName)}>{linksComponent}</div>
                </div>
            </div>
        </div>
    )
}

export default React.memo(PageContainer)
