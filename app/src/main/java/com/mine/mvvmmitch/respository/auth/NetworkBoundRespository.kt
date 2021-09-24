package com.mine.mvvmmitch.respository.auth

import ErrorHandling.Companion.ERROR_CHECK_NETWORK_CONNECTION
import ErrorHandling.Companion.ERROR_UNKNOWN
import ErrorHandling.Companion.UNABLE_TODO_OPERATION_WO_INTERNET
import ErrorHandling.Companion.UNABLE_TO_RESOLVE_HOST
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mine.mvvmmitch.di.ui.Response
import com.mine.mvvmmitch.di.ui.ResponseType
import com.mine.mvvmmitch.di.ui.auth.DataState
import com.mine.mvvmmitch.utill.ApiEmptyResponse
import com.mine.mvvmmitch.utill.ApiErrorResponse
import com.mine.mvvmmitch.utill.ApiSuccessResponse
import com.mine.mvvmmitch.utill.Constants.Companion.NETWORK_TIMEOUT
import com.mine.mvvmmitch.utill.Constants.Companion.TESTING_NETWORK_DELAY
import com.mine.mvvmmitch.utill.GenericApiResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundRespository<ResponseObject, ViewStateType>(
    isNetworkAvailable: Boolean
) {
    private  val TAG = "NetworkBoundRespository"
    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job:CompletableJob
    protected lateinit var coroutineScope: CoroutineScope


    init {
        setJob(initJob())
        setValue(DataState.loading(isLoading = true, cachedData = null))

        if(isNetworkAvailable){
            coroutineScope.launch {

                // simulate a network delay for testing
                delay(TESTING_NETWORK_DELAY)

                withContext(Main){

                    // make network call
                    val apiResponse = createCall()
                    result.addSource(apiResponse){ response ->
                        result.removeSource(apiResponse)

                        coroutineScope.launch {
                            handleNetworkCall(response)
                        }
                    }
                }
            }

            GlobalScope.launch(IO){
                delay(NETWORK_TIMEOUT)

                if(!job.isCompleted){
                    Log.e(TAG, "NetworkBoundResource: JOB NETWORK TIMEOUT." )
                    job.cancel(CancellationException(UNABLE_TO_RESOLVE_HOST))
                }
            }
        }
        else{
            onErrorReturn(UNABLE_TODO_OPERATION_WO_INTERNET, shouldUseDialog = true, shouldUseToast = false)
        }
    }

    suspend fun handleNetworkCall(response: GenericApiResponse<ResponseObject>?) {
        when(response){
            is ApiSuccessResponse ->{
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse ->{
                Log.e(TAG, "NetworkBoundResource: ${response.errorMessage}" )
                onErrorReturn(response.errorMessage, true, false)
            }
            is ApiEmptyResponse ->{
                Log.e(TAG, "NetworkBoundResource: Request returned NOTHING (HTTP 204)" )
                onErrorReturn("HTTP 204. Returned nothing.", true, false)
            }
        }
    }



    fun initJob(): Job {
        job = Job()
        job.invokeOnCompletion(onCancelling = true,invokeImmediately = true) {cause ->
            if(job.isCancelled){
                Log.e(TAG, "initJob: job is cancelled ", )
                cause?.let {
                    // do something
                }
            }else if(job.isCompleted){
                Log.e(TAG, "initJob: job is completed", )
            }

        }
        coroutineScope = CoroutineScope(Dispatchers.IO + job)
        return job
    }

    fun onErrorReturn(errorMessage: String?, shouldUseDialog: Boolean, shouldUseToast: Boolean){
        var msg = errorMessage
        var useDialog = shouldUseDialog
        var responseType: ResponseType = ResponseType.None()
        if(msg == null){
            msg = ERROR_UNKNOWN
        }
        else if(ErrorHandling.isNetworkError(msg)){
            msg = ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }
        if(shouldUseToast){
            responseType = ResponseType.Toast()
        }
        if(useDialog){
            responseType = ResponseType.Dialog()
        }

        onCompleteJob(DataState.error(
            response = Response(
                message = msg,
                responseType = responseType
            )
        ))
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>){
        GlobalScope.launch(Main){
            job.complete()
            setValue(dataState)
        }
    }

    private fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }
    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun setJob(job: Job)
}