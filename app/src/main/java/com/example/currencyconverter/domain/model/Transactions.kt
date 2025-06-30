package com.example.currencyconverter.domain.model

import java.time.LocalDateTime

data class Transactions(
    val transactionFrom: Currency,
    val transactionTo: Currency,
    val fromAmount: Double,
    val toAmount: Double,
    val timestamp: LocalDateTime
)