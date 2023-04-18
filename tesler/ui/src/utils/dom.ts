export function getAncestorByClass(element: HTMLElement, className: string): any {
    if (element.parentElement) {
        if (element.parentElement.classList.contains(className)) {
            return element.parentElement
        }

        return getAncestorByClass(element.parentElement, className)
    }

    return null
}
