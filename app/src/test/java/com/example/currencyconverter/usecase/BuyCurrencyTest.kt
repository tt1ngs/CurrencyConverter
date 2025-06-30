package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.Transactions
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.domain.repository.TransactionsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class BuyCurrencyTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var buyCurrency: BuyCurrency

    @Before
    fun setUp() {
        accountRepository = mock(AccountRepository::class.java)
        transactionsRepository = mock(TransactionsRepository::class.java)
        buyCurrency = BuyCurrency(accountRepository, transactionsRepository)
    }

    @Test
    fun `успешная покупка валюты обновляет счета и сохраняет транзакцию`() = runTest {
        val from = Currency.RUB
        val to = Currency.USD
        val fromAccount = Account(from, 10000.0)
        val toAccount = Account(to, 100.0)
        val fromAmount = 5000.0
        val toAmount = 50.0
        whenever(accountRepository.getAccountByCurrency(from)).thenReturn(fromAccount)
        whenever(accountRepository.getAccountByCurrency(to)).thenReturn(toAccount)

        buyCurrency(from, to, fromAmount, toAmount)

        verify(accountRepository).updateAccount(fromAccount.copy(amount = 5000.0))
        verify(accountRepository).updateAccount(toAccount.copy(amount = 150.0))
        verify(transactionsRepository).insertTransaction(any())
    }

    @Test
    fun `покупка валюты с недостаточным балансом выбрасывает исключение`() = runTest {
        val from = Currency.RUB
        val to = Currency.USD
        val fromAccount = Account(from, 100.0)
        val fromAmount = 500.0
        val toAmount = 5.0
        whenever(accountRepository.getAccountByCurrency(from)).thenReturn(fromAccount)
        whenever(accountRepository.getAccountByCurrency(to)).thenReturn(null)

        try {
            buyCurrency(from, to, fromAmount, toAmount)
            org.junit.Assert.fail("Должно быть выброшено IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // Ожидаемое поведение
        }
    }

    @Test
    fun `покупка валюты создает новый счет если его не было`() = runTest {
        val from = Currency.RUB
        val to = Currency.EUR
        val fromAccount = Account(from, 10000.0)
        val fromAmount = 1000.0
        val toAmount = 10.0
        whenever(accountRepository.getAccountByCurrency(from)).thenReturn(fromAccount)
        whenever(accountRepository.getAccountByCurrency(to)).thenReturn(null)

        buyCurrency(from, to, fromAmount, toAmount)

        verify(accountRepository).updateAccount(fromAccount.copy(amount = 9000.0))
        verify(accountRepository).updateAccount(Account(to, toAmount))
        verify(transactionsRepository).insertTransaction(any())
    }
}