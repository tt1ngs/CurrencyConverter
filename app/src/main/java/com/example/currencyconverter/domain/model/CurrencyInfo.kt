package com.example.currencyconverter.domain.model

data class CurrencyInfo(
    val currency: Currency,
    val name: String,
    val value: Double,
    val flagUrl: String,
    val balance: Double
)