package com.mine.mvvmmitch.openapi.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mine.mvvmmitch.models.AccountProperties

@Dao
interface AccountPersistenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(accountProperties: AccountProperties):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties):Long

    @Query("SELECT * FROM account_properties WHERE pk =:pk")
    fun searchByPK(pk:Int):AccountProperties?

    @Query("SELECT * FROM account_properties WHERE email = :email")
    fun searchByEmail(email:String):AccountProperties?
}
