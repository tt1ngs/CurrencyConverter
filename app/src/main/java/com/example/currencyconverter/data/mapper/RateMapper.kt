package com.example.currencyconverter.data.mapper

import com.example.currencyconverter.data.remote.dto.RateDto
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.CurrencyInfo

fun RateDto.toDomain(balance: Double): CurrencyInfo {
    val currency = Currency.valueOf(this.currency)
    return  CurrencyInfo(
        currency = currency,
        name = currency.name,
        value = value,
        flagUrl = "https://www.xe.com/svgs/flags/${currency.name.lowercase()}.static.svg",
        balance = balance
    )
}