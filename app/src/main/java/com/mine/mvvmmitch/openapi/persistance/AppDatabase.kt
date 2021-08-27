package com.mine.mvvmmitch.openapi.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mine.mvvmmitch.models.AccountProperties
import com.mine.mvvmmitch.models.AuthToken

@Database(entities = [AuthToken::class,AccountProperties::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAuthToken(): AuthTokenDao
    abstract fun getAccountProperties():AccountPersistenceDao

    companion object{
        const val DATABASE_NAME = "app_db"
    }
}