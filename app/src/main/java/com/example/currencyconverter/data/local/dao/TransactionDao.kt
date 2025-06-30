package com.example.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconverter.data.local.entity.TransactionDbo

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertAll(vararg transactions: TransactionDbo)

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDbo>
}