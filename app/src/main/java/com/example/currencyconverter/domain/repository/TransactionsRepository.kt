package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.Transactions
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
    suspend fun getAllTransactions(): List<Transactions>
    fun getAllTransactionsFlow(): Flow<List<Transactions>>
    suspend fun insertTransaction(transaction: Transactions)
}
