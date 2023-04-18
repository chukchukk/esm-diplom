import moment from 'moment'

export const getFormattedDate = (dateString: string) => {
    return moment(dateString).format('YYYY.MM.DD')
}
export const getFormattedDateReversed = (dateString: string) => {
    return moment(dateString).format('DD.MM.YYYY')
}

export const getFormattedFullDate = (dateString: string) => {
    return moment(dateString).format('DD.MM.YYYY HH:mm:ss')
}

export const getFormattedDateString = (dateString: string, format = 'DD.MM.YYYY') => moment(dateString).format(format)
