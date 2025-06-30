package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalances @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend fun getAll(): List<Account> = accountRepository.getAllAccounts()
    fun getAllFlow(): Flow<List<Account>> = accountRepository.getAllAccountsFlow()
}