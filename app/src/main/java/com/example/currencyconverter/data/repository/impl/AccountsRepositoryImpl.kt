package com.example.currencyconverter.data.repository.impl

import com.example.currencyconverter.data.local.dao.AccountDao
import com.example.currencyconverter.data.mapper.toDomain
import com.example.currencyconverter.data.mapper.toDbo
import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountsRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAll().map { it.toDomain() }
    }

    override fun getAllAccountsFlow(): Flow<List<Account>> {
        return accountDao.getAllAsFlow().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.insertAll(account.toDbo())
    }

    override suspend fun getAccountByCurrency(currency: Currency): Account? {
        return accountDao.getAll().map { it.toDomain() }.find { it.currencyCode == currency }
    }
}