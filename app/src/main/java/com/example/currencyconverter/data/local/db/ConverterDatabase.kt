package com.example.currencyconverter.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.data.local.dao.AccountDao
import com.example.currencyconverter.data.local.entity.AccountDbo
import com.example.currencyconverter.data.local.converter.Converters
import com.example.currencyconverter.data.local.dao.TransactionDao
import com.example.currencyconverter.data.local.entity.TransactionDbo

@Database(entities = [AccountDbo::class, TransactionDbo::class], version = 1)
@TypeConverters(Converters::class)
abstract class ConverterDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}