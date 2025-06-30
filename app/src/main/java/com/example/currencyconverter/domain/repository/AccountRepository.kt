package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getAllAccounts(): List<Account>
    fun getAllAccountsFlow(): Flow<List<Account>>
    suspend fun updateAccount(account: Account)
    suspend fun getAccountByCurrency(currency: Currency): Account?
}
