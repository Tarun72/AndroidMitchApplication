package com.mine.mvvmmitch.di.ui.auth


import com.mine.mvvmmitch.auth.api.OpenApiAuthService
import com.mine.mvvmmitch.openapi.persistance.AccountPersistenceDao
import com.mine.mvvmmitch.openapi.persistance.AuthTokenDao
import com.mine.mvvmmitch.respository.auth.AuthRepository
import com.mine.mvvmmitch.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideFakeApiService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPersistenceDao,
        openApiAuthService: OpenApiAuthService
    ): AuthRepository {
        return AuthRepository(
            authTokenDao = authTokenDao,
            accountPersistenceDao = accountPropertiesDao,
            openApiAuthService = openApiAuthService,
            sessionManager = sessionManager
        )
    }

}