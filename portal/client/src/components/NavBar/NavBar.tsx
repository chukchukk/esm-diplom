import React from 'react'
import styles from './NavBar.module.scss'
import CustomIcon from '../CustomIcon'
import { NavLink } from 'react-router-dom'

export const NavBar: React.FC = () => {
    return (
        <header className={styles.header}>
            <div className={styles.logo}>
                <h1>Система обработки заявок</h1>
                <div className={styles.logoContainer}>
                    <CustomIcon type="logo" />
                </div>
            </div>
            <nav className={styles.nav}>
                <ul className={styles.navWrapper}>
                    <li className={styles.navWrapperItem}>
                        <NavLink to="/my-requests" className={isActive => (isActive && 'primaryLinkActive') || ''}>
                            Мои заявки
                        </NavLink>
                    </li>
                </ul>
            </nav>
        </header>
    )
}
