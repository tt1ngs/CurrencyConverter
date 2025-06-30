package com.example.currencyconverter.data.mapper

import com.example.currencyconverter.data.local.entity.AccountDbo
import com.example.currencyconverter.domain.model.Account
import com.example.currencyconverter.domain.model.Currency

fun AccountDbo.toDomain(): Account {
    return Account(
        currencyCode = Currency.valueOf(code),
        amount = amount
    )
}

fun Account.toDbo(): AccountDbo {
    return AccountDbo(
        code = currencyCode.name,
        amount = amount
    )
}