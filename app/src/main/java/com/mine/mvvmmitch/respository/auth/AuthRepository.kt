package com.mine.mvvmmitch.respository.auth

import com.mine.mvvmmitch.auth.OpenApiAuthService
import com.mine.mvvmmitch.openapi.persistance.AccountPersistenceDao
import com.mine.mvvmmitch.openapi.persistance.AuthTokenDao
import com.mine.mvvmmitch.session.SessionManager
import javax.inject.Inject

class AuthRepository
@Inject constructor(authTokenDao: AuthTokenDao,
                    openApiAuthService: OpenApiAuthService,
                    sessionManager: SessionManager,
                    accountPersistenceDao: AccountPersistenceDao) {
}