package com.example.currencyconverter.data.mapper

import com.example.currencyconverter.data.local.entity.TransactionDbo
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.model.Transactions

fun TransactionDbo.toDomain() : Transactions {
    return Transactions(
        transactionFrom = Currency.valueOf(from),
        transactionTo = Currency.valueOf(to),
        fromAmount = fromAmount,
        toAmount = toAmount,
        timestamp = dateTime
    )
}

fun Transactions.toDbo() : TransactionDbo {
    return TransactionDbo(
        from = transactionFrom.name,
        to = transactionTo.name,
        fromAmount = fromAmount,
        toAmount = toAmount,
        dateTime = timestamp
    )
}