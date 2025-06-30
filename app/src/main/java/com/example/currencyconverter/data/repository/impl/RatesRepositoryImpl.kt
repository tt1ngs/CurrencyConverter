package com.example.currencyconverter.data.repository.impl

import com.example.currencyconverter.data.local.dao.AccountDao
import com.example.currencyconverter.data.mapper.toDomain as rateToDomain
import com.example.currencyconverter.data.remote.RatesService
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.domain.repository.RatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class RatesRepositoryImpl(
    private val ratesService: RatesService,
    private val accountDao: AccountDao
) : RatesRepository {
    override suspend fun getRates(base: Currency, amount: Double): List<CurrencyInfo> {
        val rates = ratesService.getRates(base.name, amount)
        val accounts = accountDao.getAll().associateBy { it.code }
        return rates.map { rateDto ->
            val balance = accounts[rateDto.currency]?.amount ?: 0.0
            rateDto.rateToDomain(balance)
        }
    }

    override fun getRatesFlow(base: Currency, amount: Double): Flow<List<CurrencyInfo>> = flow {
        while (true) {
            emit(getRates(base, amount))
            delay(1000)
        }
    }
}