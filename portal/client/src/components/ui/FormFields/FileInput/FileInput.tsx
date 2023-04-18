import React, { MutableRefObject, useCallback, useEffect, useRef } from 'react'
import styles from './FileInput.module.scss'
import { Field } from '../../../../interfaces/request'
import { useFormContext, useWatch } from 'react-hook-form'
import addImg from '../../../../assets/icons/add.svg'
import addImgDisabled from '../../../../assets/icons/addDisabled.svg'
import { mergeRefs } from '../../../../utils/mergeRefs'
import cn from 'classnames'
import CustomIcon from '../../../CustomIcon'
import useLocalStateContext from '../../../../form-config/hooks/useLocalStateContext'

interface FileInputProps {
    field: Field
    multiple?: boolean
    disabled?: boolean
}

function FileInput({ field, multiple, disabled }: FileInputProps) {
    const { localDispatch, localState } = useLocalStateContext()
    const { register, control } = useFormContext()
    const myFileRef = useRef<HTMLInputElement>(null)
    const { ref: fileRef, onChange: onFileChange, ...rest } = register(field.option.fieldName, { required: field.isRequired })
    const clickRef = useCallback(() => {
        ;(myFileRef as unknown as MutableRefObject<HTMLInputElement>).current?.click()
    }, [myFileRef])
    const v = useWatch({ control, name: field.option.fieldName })

    useEffect(() => {
        if (v?.length) {
            localDispatch?.({
                type: 'addFiles',
                payload: {
                    fieldName: field.option.fieldName,
                    files: Array.prototype.slice.call(v)
                }
            })
        }
    }, [field.option.fieldName, localDispatch, v])

    return (
        <div className={styles.container}>
            <div
                className={cn(styles.tagsWrapper, {
                    [styles.placeholder]: !localState?.files[field.option.fieldName]?.length,
                    [styles.pale]: disabled
                })}
            >
                {!localState?.files[field.option.fieldName]?.length && field.option.placeholder}
                {localState?.files[field.option.fieldName]?.map((i: File, index: number) => (
                    <span className={styles.fileTag} key={i.name}>
                        {i.name}
                        <button
                            className={styles.deleteBtn}
                            type="button"
                            onClick={() =>
                                localDispatch?.({ type: 'removeFile', payload: { fieldName: field.option.fieldName, fileIndex: index } })
                            }
                        >
                            <CustomIcon type="clear" />
                        </button>
                    </span>
                ))}
            </div>
            <input
                disabled={disabled}
                className={styles.self}
                multiple={multiple}
                type="file"
                placeholder={field.option.placeholder}
                ref={mergeRefs([fileRef, myFileRef])}
                onChange={onFileChange}
                {...rest}
            />
            <img
                className={cn(styles.icon, { [styles.disabled]: disabled })}
                src={disabled ? addImgDisabled : addImg}
                alt="add file"
                onClick={clickRef}
            />
        </div>
    )
}

export default FileInput
