package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    suspend fun getRates(base: Currency, amount: Double): List<CurrencyInfo>
    fun getRatesFlow(base: Currency, amount: Double): Flow<List<CurrencyInfo>>
}
