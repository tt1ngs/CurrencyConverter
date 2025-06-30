package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Transactions
import com.example.currencyconverter.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactions @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {
    suspend fun getAll(): List<Transactions> = transactionsRepository.getAllTransactions()
    fun getAllFlow(): Flow<List<Transactions>> = transactionsRepository.getAllTransactionsFlow()
}