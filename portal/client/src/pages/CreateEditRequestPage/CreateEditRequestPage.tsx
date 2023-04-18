import React, { useCallback, useEffect } from 'react'
import PageContainer from '../../components/ui/PageContainer/PageContainer'
import NavigationLink from '../../components/ui/NavigationLink/NavigationLink'
import {
    useCreateRequestMutation,
    useEditRequestMutation,
    useGetDictionariesQuery,
    useGetRequestQuery,
    useSendFilesMutation
} from '../../store/esmService'
import { AnyRequestType, Category, personalDataChangeTypes } from '../../interfaces/request'
import Loader from '../../components/ui/Loader/Loader'
import { FormProvider, useForm } from 'react-hook-form'
import styles from './CreateEditRequestPage.module.scss'
import FormItem from '../../components/ui/FormItem/FormItem'
import cn from 'classnames'
import { useNavigate, useParams, useSearchParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { clearRequestInfo, setCreationLock, setRequestCategory, setRequestType } from '../../store/reducers/request'
import { availableRequestTypesOptions } from '../../form-config/request-types'
import { skipToken } from '@reduxjs/toolkit/query'
import { FormComponent } from '../../interfaces/form'
import { REQUEST_TYPE_QUERY_PARAMETER } from '../../constants/text'
import { LocalStateContext } from '../../form-config/context/local-state-context'
import useLocalStateReducer from '../../form-config/hooks/useLocalStateReducer'
import CustomSelect from '../../components/ui/CustomSelect/CustomSelect'

const selectTypeField = {
    isRequired: false,
    option: {
        fieldName: 'type',
        fieldType: 'Select',
        title: 'Тип заявки',
        placeholder: 'Выбери тип заявки'
    }
}

interface CreateRequestPageProps {
    category: Category
    pageTitle: string
}
const creationLockRequestTypes: Array<AnyRequestType> = [personalDataChangeTypes.DOCUMENTS_CHANGE]

function CreateEditRequestPage({ category, pageTitle }: CreateRequestPageProps) {
    const [localState, localDispatch] = useLocalStateReducer()
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const { requestId } = useParams()
    const [search, setSearch] = useSearchParams()

    useEffect(() => {
        dispatch(setRequestCategory(category))
    }, [category, dispatch])

    const { isLoading: isDictionariesLoading } = useGetDictionariesQuery(category)

    const [send, { isError, error: sendError }] = useCreateRequestMutation()
    const [edit, { isError: isEditError, error: editError, isSuccess: isEditSuccess }] = useEditRequestMutation()
    const [sendFiles] = useSendFilesMutation()

    const { data: presentRequestData, isLoading: isPresentRequestDataLoading } = useGetRequestQuery(requestId ?? skipToken, {
        refetchOnMountOrArgChange: true
    })

    const isLoading = isDictionariesLoading || isPresentRequestDataLoading

    const { requestType, creationLocked } = useSelector((store: RootState) => ({
        requestType: store.request.type,
        creationLocked: store.request.creationLock
    }))

    useEffect(() => {
        const requestTypeParam = search.get(REQUEST_TYPE_QUERY_PARAMETER)
        if (requestTypeParam && requestType !== requestTypeParam) {
            dispatch(setRequestType(requestTypeParam))
            if (creationLockRequestTypes.some(i => requestTypeParam === i)) {
                dispatch(setCreationLock(true))
            } else {
                dispatch(setCreationLock(undefined))
            }
        }
        if (!requestTypeParam && !requestId) {
            setSearch({ type: availableRequestTypesOptions[category][0].key })
        }
        if (requestId) {
            dispatch(setCreationLock(undefined))
        }
    }, [category, dispatch, requestId, requestType, search, setSearch])

    useEffect(() => {
        if (isError) {
            alert(JSON.stringify(sendError))
        }
        if (isEditError) {
            alert(JSON.stringify(editError))
        }
    }, [dispatch, editError, isEditError, isEditSuccess, isError, navigate, requestId, sendError])

    const methods = useForm<Record<string, any>>({
        defaultValues: presentRequestData
    })

    const handleMyRequestsRedirect = React.useCallback(() => {
        navigate('/my-requests')
        methods.reset()
        dispatch(setCreationLock(undefined))
    }, [dispatch, methods, navigate])

    React.useEffect(() => {
        methods.reset(presentRequestData)
    }, [presentRequestData, methods])

    const handleRequestTypeChange = useCallback(
        (value: string) => {
            setSearch({ type: value })
            methods.reset()
        },
        [methods, setSearch]
    )
    const onSubmit = (v: any) => {
        const data = { ...v, type: requestType }
        let files: File[] = Object.values(localState.files).flat(1)
        Object.entries(data).forEach(([key, i]) => {
            if (i instanceof FileList) {
                delete data[key]
            }
        })
        if (requestId) {
            edit({ requestId, data })
                .unwrap()
                .then(() => sendFiles({ files, requestId }))
                .then(() => {
                    dispatch(clearRequestInfo())
                    navigate(`/my-requests/${requestId}`)
                })
                .catch(e => alert(JSON.stringify(e, null, 2)))
        } else {
            dispatch(setCreationLock(undefined))
            send({ data })
                .unwrap()
                .then(r => sendFiles({ files, requestId: r.toString() }))
                .then(() => {
                    dispatch(clearRequestInfo())
                    handleMyRequestsRedirect()
                })
                .catch(e => alert(JSON.stringify(e, null, 2)))
        }
    }
    const currentCategory = availableRequestTypesOptions[category]
    let RestFieldsComponent: FormComponent | null = null
    for (const t of currentCategory) {
        if (t.key === requestType) {
            RestFieldsComponent = t.component
            break
        }
    }
    return (
        <PageContainer
            mainColumnClassName={styles.mainColumn}
            title={pageTitle}
            linksComponent={<NavigationLink to="/my-requests" label="К ЗАЯВКАМ" />}
        >
            {isLoading && <Loader />}
            {!isLoading && (
                <FormProvider {...methods}>
                    <form onSubmit={methods.handleSubmit(onSubmit)}>
                        {RestFieldsComponent && (
                            <LocalStateContext.Provider value={{ localState, localDispatch }}>
                                <RestFieldsComponent
                                    edit={!!requestId}
                                    selectTypeComponent={
                                        <FormItem
                                            label={selectTypeField.option.title}
                                            key={selectTypeField.option.fieldName}
                                            fieldName={selectTypeField.option.fieldName}
                                            disabled={!!requestId}
                                        >
                                            <CustomSelect
                                                value={requestType}
                                                options={availableRequestTypesOptions[category]}
                                                disabled={!!requestId}
                                                onChange={handleRequestTypeChange}
                                                placeholder={selectTypeField.option.placeholder}
                                            />
                                        </FormItem>
                                    }
                                />
                            </LocalStateContext.Provider>
                        )}
                        <div className={styles.requestFormButtons}>
                            <button className={cn('cancelButton', 'button')} onClick={handleMyRequestsRedirect}>
                                Отменить
                            </button>
                            <button className="button" type="submit" disabled={creationLocked}>
                                {requestId ? 'Сохранить' : 'Создать'}
                            </button>
                        </div>
                    </form>
                </FormProvider>
            )}
        </PageContainer>
    )
}

export default React.memo(CreateEditRequestPage)
