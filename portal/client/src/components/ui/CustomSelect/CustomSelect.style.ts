import { StylesConfig } from 'react-select'
import { ThemeConfig } from 'react-select/src/theme'

const alignCenter = {
    alignItems: 'center',
    display: 'flex'
}

const fontSize = '14px'
const height = fontSize

const color = {
    optionBackground: '#F2F9FF',
    option: '#FB6507',
    placeholder: '#959DAC'
}

const getMenuWidthFromFormItemWrapper = (paddingLeft: number, paddingRight: number, currentWidth: string) => {
    return {
        width: `calc(${currentWidth} + ${paddingRight + paddingLeft}px)`,
        margin: `11px -${paddingRight}px 0 -${paddingLeft}px`
    }
}

export const selectStyles: StylesConfig<any, false> = {
    control: styles => ({
        ...styles,
        backgroundColor: 'inherit',
        border: 'none',
        minHeight: height,
        maxHeight: height,
        fontSize,
        lineHeight: 1,
        fontFamily: 'inherit',
        fontWeight: 'inherit',
        alignItems: 'flex-start',
        color: 'inherit',
        letterSpacing: 'inherit',
        ':hover': {
            cursor: 'pointer'
        }
    }),
    menu: styles => {
        return {
            ...styles,
            ...getMenuWidthFromFormItemWrapper(14, 12, styles.width as string),
            borderRadius: 0,
            borderBottomLeftRadius: '3px',
            borderBottomRightRadius: '3px',
            boxShadow: '0px 5px 10px rgba(42, 43, 55, 0.15)',
            letterSpacing: 'inherit'
        }
    },
    option: (styles, { isDisabled }) => {
        return {
            ...styles,
            ...alignCenter,
            backgroundColor: 'transparent',
            color: 'black',
            cursor: isDisabled ? 'not-allowed' : 'default',
            fontSize,
            lineHeight: 1,
            height: '28px',
            ':hover': {
                ...styles[':hover'],
                backgroundColor: color.optionBackground,
                color: color.option,
                cursor: 'pointer'
            },
            ':active': {
                ...styles[':active'],
                backgroundColor: color.optionBackground,
                border: 'none'
            }
        }
    },
    input: styles => ({ ...styles, ...alignCenter, margin: 0, padding: 0, height }),
    placeholder: styles => ({ ...styles, ...alignCenter, color: color.placeholder, letterSpacing: 'inherit' }),
    singleValue: styles => ({ ...styles, ...alignCenter, margin: 0, position: 'relative', color: 'inherit', letterSpacing: 'inherit' }),
    valueContainer: styles => ({ ...styles, width: '100%', padding: 0, height })
}

export const selectTheme: ThemeConfig = theme => ({
    ...theme,
    colors: {
        ...theme.colors,
        primary: 'transparent'
    }
})
