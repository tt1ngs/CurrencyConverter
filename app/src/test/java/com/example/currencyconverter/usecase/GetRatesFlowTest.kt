package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.domain.repository.RatesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetRatesFlowTest {
    private lateinit var ratesRepository: RatesRepository
    private lateinit var getRatesFlow: GetRatesFlow

    @Before
    fun setUp() {
        ratesRepository = mock(RatesRepository::class.java)
        getRatesFlow = GetRatesFlow(ratesRepository)
    }

    @Test
    fun `invoke возвращает поток курсов валют`() = runTest {
        val base = Currency.USD
        val amount = 100.0
        val expected = listOf(
            CurrencyInfo(Currency.USD, "USD", 100.0, "url", 1000.0)
        )
        whenever(ratesRepository.getRatesFlow(base, amount)).thenReturn(flow { emit(expected) })

        val result = getRatesFlow(base, amount).first()
        assertEquals(expected, result)
    }
}