package com.example.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.local.entity.AccountDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(vararg accounts: AccountDbo)

    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountDbo>

    @Query("SELECT * FROM accounts")
    fun getAllAsFlow(): Flow<List<AccountDbo>>
}