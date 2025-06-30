package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.domain.repository.RatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRatesFlow @Inject constructor(
    private val ratesRepository: RatesRepository
) {
    operator fun invoke(base: Currency, amount: Double): Flow<List<CurrencyInfo>> =
        ratesRepository.getRatesFlow(base, amount)
}
