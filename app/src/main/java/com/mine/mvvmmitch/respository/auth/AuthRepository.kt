package com.mine.mvvmmitch.respository.auth

import androidx.lifecycle.LiveData
import com.mine.mvvmmitch.auth.api.OpenApiAuthService
import com.mine.mvvmmitch.auth.network_response.LoginResponse
import com.mine.mvvmmitch.auth.network_response.RegistrationResponse
import com.mine.mvvmmitch.openapi.persistance.AccountPersistenceDao
import com.mine.mvvmmitch.openapi.persistance.AuthTokenDao
import com.mine.mvvmmitch.session.SessionManager
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
}