package com.mine.mvvmmitch.respository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.mine.mvvmmitch.auth.api.OpenApiAuthService
import com.mine.mvvmmitch.auth.network_response.LoginResponse
import com.mine.mvvmmitch.auth.network_response.RegistrationResponse
import com.mine.mvvmmitch.di.ui.Response
import com.mine.mvvmmitch.di.ui.ResponseType
import com.mine.mvvmmitch.di.ui.auth.DataState
import com.mine.mvvmmitch.di.ui.auth.state.AuthViewState
import com.mine.mvvmmitch.models.AuthToken
import com.mine.mvvmmitch.openapi.persistance.AccountPersistenceDao
import com.mine.mvvmmitch.openapi.persistance.AuthTokenDao
import com.mine.mvvmmitch.session.SessionManager
import com.mine.mvvmmitch.utill.ApiEmptyResponse
import com.mine.mvvmmitch.utill.ApiErrorResponse
import com.mine.mvvmmitch.utill.ApiSuccessResponse
import com.mine.mvvmmitch.utill.ErrorHandler.Companion.ERROR_UNKNOWN
import com.mine.mvvmmitch.utill.GenericApiResponse
import javax.inject.Inject

class AuthRepository
@Inject constructor(
    val authTokenDao: AuthTokenDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager,
    val accountPersistenceDao: AccountPersistenceDao
) {

    fun testLogin(email: String, password: String): LiveData<GenericApiResponse<LoginResponse>> {
        return openApiAuthService.login(
            email = email,
            password = password
        )
    }

    fun testRegister(email: String, username: String, password: String, password2: String):
            LiveData<GenericApiResponse<RegistrationResponse>> {
        return openApiAuthService.register(
            email = email, password = password,
            password2 = password2, username = username
        )
    }


    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>>{
        return openApiAuthService.login(email, password)
            .switchMap { response ->
                object: LiveData<DataState<AuthViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(response){
                            is ApiSuccessResponse ->{
                                value = DataState.data(
                                    AuthViewState(
                                        authToken = AuthToken(response.body.pk, response.body.token)
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse ->{
                                value = DataState.error(
                                    Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse ->{
                                value = DataState.error(
                                    Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }


    fun attemptRegistration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>>{
        return openApiAuthService.register(email, username, password, confirmPassword)
            .switchMap { response ->
                object: LiveData<DataState<AuthViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        when(response){
                            is ApiSuccessResponse ->{
                                value = DataState.data(
                                    AuthViewState(
                                        authToken = AuthToken(response.body.pk, response.body.token)
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse ->{
                                value = DataState.error(
                                    Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse ->{
                                value = DataState.error(
                                    Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }

}