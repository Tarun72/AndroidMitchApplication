package com.mine.mvvmmitch.session

import android.app.Application
import com.mine.mvvmmitch.openapi.persistance.AuthTokenDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(authTokenDao: AuthTokenDao,application: Application) {
}