package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.repository.AccountRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetBalancesTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var getBalances: GetBalances

    @Before
    fun setUp() {
        accountRepository = mock(AccountRepository::class.java)
        getBalances = GetBalances(accountRepository)
    }

    @Test
    fun `getAll возвращает список аккаунтов`() = runTest {
        val expected = listOf(Account(com.example.currencyconverter.domain.model.Currency.USD, 100.0))
        whenever(accountRepository.getAllAccounts()).thenReturn(expected)
        val result = getBalances.getAll()
        assertEquals(expected, result)
    }

    @Test
    fun `getAllFlow возвращает flow аккаунтов`() = runTest {
        val expected = listOf(Account(com.example.currencyconverter.domain.model.Currency.USD, 100.0))
        whenever(accountRepository.getAllAccountsFlow()).thenReturn(flowOf(expected))
        val result = getBalances.getAllFlow().first()
        assertEquals(expected, result)
    }
}