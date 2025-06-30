package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.remote.dto.RateDto

interface RatesService {
    suspend fun getRates(
        baseCurrencyCode: String,
        amount: Double
    ): List<RateDto>
}