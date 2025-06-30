package com.example.currencyconverter.data.repository.impl

import com.example.currencyconverter.data.local.dao.TransactionDao
import com.example.currencyconverter.data.mapper.toDomain
import com.example.currencyconverter.data.mapper.toDbo
import com.example.currencyconverter.domain.model.Transactions
import com.example.currencyconverter.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionsRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionsRepository {
    override suspend fun getAllTransactions(): List<Transactions> {
        return transactionDao.getAll().map { it.toDomain() }
    }

    override fun getAllTransactionsFlow(): Flow<List<Transactions>> = flow {
        emit(getAllTransactions())
    }

    override suspend fun insertTransaction(transaction: Transactions) {
        transactionDao.insertAll(transaction.toDbo())
    }
}