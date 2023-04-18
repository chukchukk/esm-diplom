import React, { useEffect, useState } from 'react'

import styles from './DocumentsChangeForm.module.scss'
import { useFormContext, UseFormReturn, useFormState, useWatch } from 'react-hook-form'
import { CommonFormProps } from '../../../interfaces/form'
import FormItem from '../../ui/FormItem/FormItem'
import { commentField, newPassportFilesField } from '../../../form-config/fields/common'
import Input from '../../ui/FormFields/Input/Input'
import { Field, FileInfo, requestByCategory } from '../../../interfaces/request'
import FileInput from '../../ui/FormFields/FileInput/FileInput'
import PopoverHint from '../../ui/PopoverHint/PopoverHint'
import SingleOrSeveralFiles from '../../ui/HintContent/SingleOrSeveralFiles/SingleOrSeveralFiles'
import { useGetDictionariesQuery } from '../../../store/esmService'
import { skipToken } from '@reduxjs/toolkit/query'
import DownloadFileLink from '../../ui/DownloadFileLink/DownloadFileLink'
import { Modal } from '../../Modal/Modal'
import cn from 'classnames'
import Select from '../../ui/FormFields/Select/Select'
import { RootState } from '../../../store/store'
import { useDispatch, useSelector } from 'react-redux'
import { setCreationLock } from '../../../store/reducers/request'
import CheckBox from '../../ui/FormFields/CheckBox/CheckBox'
import SimpleText from '../../ui/HintContent/SimpleText/SimpleText'
import UnapWarning from '../../ui/HintContent/UnapWarning/UnapWarning'
import { NEW_DATA_CHANGE } from '../../../constants/text'
import WarningPanel from '../../ui/WarningPanel/WarningPanel'
import useLocalStateContext from '../../../form-config/hooks/useLocalStateContext'
import { useCustomValueSetter } from '../../../hooks/useCustomValueSetter'

const changeNameApplication: Field = {
    isRequired: true,
    option: {
        fieldName: 'changeNameApplication',
        title: 'Заявление на смену фамилии',
        placeholder: 'Прикрепи подписанное заявление на смену фамилии',
        fieldType: 'File'
    }
}
const changeSnils: Field = {
    isRequired: true,
    option: {
        fieldName: 'changeSnils',
        title: 'Смена СНИЛС',
        placeholder: 'Выбери значение',
        fieldType: 'Select'
        // valuesKey: 'snilsChangeTypes'
    }
}
const changeSnilsApplication: Field = {
    isRequired: true,
    option: {
        fieldName: 'changeSnilsApplication',
        title: 'Заявление на смену СНИЛС',
        placeholder: 'Прикрепи подписанное заявление на смену СНИЛС',
        fieldType: 'File'
    }
}
const newSnils: Field = {
    isRequired: false,
    option: {
        fieldName: 'newSnils',
        title: 'СНИЛС',
        placeholder: 'Прикрепи переоформленный СНИЛС',
        fieldType: 'File'
    }
}
const marriageNameChange: Field = {
    isRequired: false,
    option: {
        fieldName: 'marriageNameChange',
        title: 'Смена фамилии в связи с заключением / расторжением брака',
        placeholder: '',
        fieldType: 'Checkbox'
    }
}
const marriageCertificate: Field = {
    isRequired: true,
    option: {
        fieldName: 'marriageCertificate',
        title: 'Свидетельство о заключении / расторжении брака ',
        placeholder: 'Прикрепи свидетельство о заключении / расторжении брака',
        fieldType: 'File'
    }
}

function DocumentsChangeForm({ selectTypeComponent, edit }: CommonFormProps) {
    const { localDispatch } = useLocalStateContext()
    const formContext = useFormContext()
    const { control, resetField } = formContext
    const { errors, isDirty } = useFormState({ control })
    const [modalVisible, setModalVisible] = useState(false)
    const { category, creationLocked } = useSelector((store: RootState) => ({
        category: store.request.category,
        creationLocked: store.request.creationLock
    }))
    const { data: dictionaries, isError } = useGetDictionariesQuery(category ?? skipToken)
    const snilsValue = useWatch({ control, name: changeSnils.option.fieldName })
    const marriageNameChangeValue = useWatch({ control, name: marriageNameChange.option.fieldName })
    const lastNameFiles = dictionaries?.lastNameChangeApplication as FileInfo[]
    const snilsFiles = dictionaries?.snilsChangeApplication as FileInfo[]
    useEffect(() => {
        if (marriageNameChangeValue === false) {
            resetField(marriageCertificate.option.fieldName)
            localDispatch?.({ type: 'clearFieldFiles', payload: { fieldName: marriageCertificate.option.fieldName } })
        }
        if (snilsValue === 'INDEPENDENTLY') {
            resetField(changeSnilsApplication.option.fieldName)
            localDispatch?.({ type: 'clearFieldFiles', payload: { fieldName: changeSnilsApplication.option.fieldName } })
        }
        if (snilsValue === 'THROUGH_HR') {
            resetField(newSnils.option.fieldName)
            localDispatch?.({ type: 'clearFieldFiles', payload: { fieldName: newSnils.option.fieldName } })
        }
    }, [localDispatch, marriageNameChangeValue, resetField, snilsValue])

    useCommonDefaultValues(formContext, edit)

    return (
        <div className={styles.formInstance}>
            {selectTypeComponent}
            {!edit && (
                <AcquaintanceBlock
                    modalVisible={modalVisible}
                    onChangeModalVisible={setModalVisible}
                    lastNameFiles={lastNameFiles}
                    snilsFiles={snilsFiles}
                    isError={isError}
                />
            )}
            {!edit && (
                <FormItem
                    label={changeNameApplication.option.title}
                    fieldName={changeNameApplication.option.fieldName}
                    required={changeNameApplication.isRequired}
                    error={errors[changeNameApplication.option.fieldName]}
                    rightComponent={
                        <DownloadApplicationsPopover edit={edit} disabled={creationLocked} files={lastNameFiles} isError={isError} />
                    }
                    disabled={creationLocked}
                >
                    <FileInput field={changeNameApplication} disabled={creationLocked} />
                </FormItem>
            )}
            {!edit && (
                <FormItem
                    label={newPassportFilesField.option.title}
                    fieldName={newPassportFilesField.option.fieldName}
                    required={newPassportFilesField.isRequired}
                    error={errors[newPassportFilesField.option.fieldName]}
                    rightComponent={<PopoverHint content={<SingleOrSeveralFiles />} disabled={creationLocked} />}
                    disabled={creationLocked}
                >
                    <FileInput field={newPassportFilesField} multiple disabled={creationLocked} />
                </FormItem>
            )}
            <FormItem
                label={changeSnils.option.title}
                fieldName={changeSnils.option.fieldName}
                required={changeSnils.isRequired}
                error={errors[changeSnils.option.fieldName]}
                disabled={creationLocked}
            >
                <Select field={changeSnils} disabled={creationLocked} options={dictionaries?.[changeSnils.option.fieldName]} />
            </FormItem>
            {snilsValue === 'INDEPENDENTLY' && (
                <FormItem
                    label={newSnils.option.title}
                    fieldName={newSnils.option.fieldName}
                    required={newSnils.isRequired}
                    error={errors[newSnils.option.fieldName]}
                    rightComponent={
                        <PopoverHint
                            content={<SimpleText text="Прикрепи новый СНИЛС, если ты его уже переоформил" />}
                            disabled={creationLocked}
                        />
                    }
                    disabled={creationLocked}
                >
                    <FileInput field={newSnils} disabled={creationLocked} />
                </FormItem>
            )}
            {snilsValue === 'THROUGH_HR' && (
                <FormItem
                    label={changeSnilsApplication.option.title}
                    fieldName={changeSnilsApplication.option.fieldName}
                    required={changeSnilsApplication.isRequired}
                    error={errors[changeSnilsApplication.option.fieldName]}
                    rightComponent={
                        <DownloadApplicationsPopover edit={edit} disabled={creationLocked} files={snilsFiles} isError={isError} />
                    }
                    disabled={creationLocked}
                >
                    <FileInput field={changeSnilsApplication} disabled={creationLocked} />
                </FormItem>
            )}
            <CheckBox field={marriageNameChange} disabled={creationLocked} />
            {marriageNameChangeValue === true && (
                <FormItem
                    label={marriageCertificate.option.title}
                    fieldName={marriageCertificate.option.fieldName}
                    required={marriageCertificate.isRequired}
                    error={errors[marriageCertificate.option.fieldName]}
                    disabled={creationLocked}
                >
                    <FileInput field={marriageCertificate} disabled={creationLocked} />
                </FormItem>
            )}
            {!edit && (
                <FormItem
                    label={commentField.option.title}
                    fieldName={commentField.option.fieldName}
                    required={commentField.isRequired}
                    error={errors[commentField.option.fieldName]}
                    disabled={creationLocked}
                >
                    <Input field={commentField} disabled={creationLocked} />
                </FormItem>
            )}
            {isDirty && <WarningPanel title={NEW_DATA_CHANGE} content={<UnapWarning />} />}
        </div>
    )
}

export default React.memo(DocumentsChangeForm)

function AcquaintanceBlock({
    modalVisible,
    onChangeModalVisible,
    lastNameFiles,
    snilsFiles,
    isError
}: {
    modalVisible: boolean
    onChangeModalVisible: (v: boolean) => void
    isError: boolean
    lastNameFiles?: FileInfo[]
    snilsFiles?: FileInfo[]
}) {
    const dispatch = useDispatch()
    const { creationLocked } = useSelector((store: RootState) => ({
        creationLocked: store.request.creationLock
    }))

    return (
        <>
            <div className={styles.actionPanel}>
                Ознакомься с{' '}
                <button type="button" className={styles.openModalButton} onClick={() => onChangeModalVisible(true)}>
                    инструкцией
                </button>{' '}
                перед подачей заявки
            </div>
            <Modal
                show={modalVisible}
                onClose={() => onChangeModalVisible(false)}
                title="Инструкция"
                footer={
                    <label className={styles.checkboxLabel}>
                        <input
                            type="checkbox"
                            checked={!creationLocked}
                            onChange={() => {
                                onChangeModalVisible(false)
                                dispatch(setCreationLock(!creationLocked))
                            }}
                            className={cn(styles.checkboxInstance, {
                                [styles.checked]: !creationLocked,
                                [styles.unchecked]: creationLocked
                            })}
                        />
                        Ознакомлен(-а)
                    </label>
                }
            >
                <InstructionContent snilsFiles={snilsFiles} lastNameFiles={lastNameFiles} isError={isError} />
            </Modal>
        </>
    )
}

function DownloadApplicationsPopover({
    edit,
    disabled,
    isError,
    files
}: {
    isError: boolean
    edit?: boolean
    disabled?: boolean
    files?: FileInfo[]
}) {
    return (
        <PopoverHint
            content={
                <div className={styles.lastNameChangeApplicationsPopover}>
                    <div>Прикрепи подписанные сканы заявлений:</div>
                    {!isError && files?.map(i => <DownloadFileLink fileId={i.key} fileName={i.value} key={i.key} />)}
                    {isError && <div style={{ color: '#fb6507' }}>Не удалось загрузить формы заявлений</div>}
                </div>
            }
            disabled={disabled}
        />
    )
}

function InstructionContent({
    edit,
    isError,
    lastNameFiles,
    snilsFiles
}: {
    isError: boolean
    edit?: boolean
    lastNameFiles?: FileInfo[]
    snilsFiles?: FileInfo[]
}) {
    return (
        <div className={styles.modalContent}>
            <ol className={cn(styles.list, styles.olList)}>
                <li>
                    До создания заявки на смену документов тебе необходимо <strong>самостоятельно переоформить банковскую карту</strong>.
                    Если твои личные данные не будут совпадать с данными карты, банк не сможет перечислить тебе зарплату.
                </li>
                <li>
                    К заявке нужно прикрепить <strong>сканы подписанных заявлений на смену фамилии</strong> (в соответствии с твоим
                    трудоустройством):
                    {!isError && (
                        <ol type="a">
                            {lastNameFiles?.map(i => (
                                <li key={i.key}>
                                    <DownloadFileLink fileId={i.key} fileName={i.value} key={i.key} />
                                </li>
                            ))}
                        </ol>
                    )}
                </li>
            </ol>
            <p className={styles.text}>Тебе нужно переоформить СНИЛС. Ты можешь сделать это:</p>
            <ul className={styles.list}>
                <li>самостоятельно, обратившись в любой территориальный орган Пенсионного фонда Российской Федерации;</li>
                <li>самостоятельно, обратившись в МФЦ;</li>
                <li>
                    через отдел кадров, прикрепив <strong>сканы подписанных заявлений на смену СНИЛС</strong>:
                    {!isError && (
                        <ol type="i">
                            {snilsFiles?.map(i => (
                                <li key={i.key}>
                                    <DownloadFileLink fileId={i.key} fileName={i.value} key={i.key} />
                                </li>
                            ))}
                        </ol>
                    )}
                </li>
            </ul>
        </div>
    )
}

function useCommonDefaultValues({ getValues, setValue }: UseFormReturn, edit?: boolean) {
    const { setDefaultValue } = useCustomValueSetter({ getValues, setValue })
    const type = useSelector((store: RootState) => store.request.type)
    const create = !edit

    useEffect(() => {
        if (create && type === requestByCategory.PERSONAL_DATA_CHANGE.DOCUMENTS_CHANGE) {
            setDefaultValue(changeSnils.option.fieldName, 'THROUGH_HR')
        }
    }, [create, setDefaultValue, type])
}
