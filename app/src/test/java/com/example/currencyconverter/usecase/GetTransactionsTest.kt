package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Transactions
import com.example.currencyconverter.domain.repository.TransactionsRepository
import java.time.LocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class GetTransactionsTest {
    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var getTransactions: GetTransactions

    @Before
    fun setUp() {
        transactionsRepository = mock(TransactionsRepository::class.java)
        getTransactions = GetTransactions(transactionsRepository)
    }

    @Test
    fun `getAll возвращает список транзакций`() = runTest {
        val expected = listOf(
            Transactions(
                transactionFrom = com.example.currencyconverter.domain.model.Currency.USD,
                transactionTo = com.example.currencyconverter.domain.model.Currency.RUB,
                fromAmount = 10.0,
                toAmount = 700.0,
                timestamp = java.time.LocalDateTime.now()
            )
        )
        org.mockito.kotlin.whenever(transactionsRepository.getAllTransactions()).thenReturn(expected)
        val result = getTransactions.getAll()
        assertEquals(expected, result)
    }

    @Test
    fun `getAllFlow возвращает flow транзакций`() = runTest {
        val expected = listOf(
            Transactions(
                transactionFrom = com.example.currencyconverter.domain.model.Currency.USD,
                transactionTo = com.example.currencyconverter.domain.model.Currency.RUB,
                fromAmount = 10.0,
                toAmount = 700.0,
                timestamp = java.time.LocalDateTime.now()
            )
        )
        org.mockito.kotlin.whenever(transactionsRepository.getAllTransactionsFlow()).thenReturn(flowOf(expected))
        val result = getTransactions.getAllFlow().first()
        assertEquals(expected, result)
    }
}