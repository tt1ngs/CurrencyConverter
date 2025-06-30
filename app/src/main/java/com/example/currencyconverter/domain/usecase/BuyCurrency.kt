package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.domain.repository.TransactionsRepository
import java.time.LocalDateTime
import javax.inject.Inject

class BuyCurrency @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionsRepository: TransactionsRepository
) {
    suspend operator fun invoke(
        from: Currency,
        to: Currency,
        fromAmount: Double,
        toAmount: Double
    ) {
        val fromAccount = accountRepository.getAccountByCurrency(from)
        val toAccount = accountRepository.getAccountByCurrency(to)
        require(fromAccount != null && fromAccount.amount >= fromAmount) { "Недостаточно средств" }
        accountRepository.updateAccount(fromAccount.copy(amount = fromAccount.amount - fromAmount))
        if (toAccount != null) {
            accountRepository.updateAccount(toAccount.copy(amount = toAccount.amount + toAmount))
        } else {
            accountRepository.updateAccount(
                com.example.currencyconverter.domain.model.Account(currencyCode = to, amount = toAmount)
            )
        }
        transactionsRepository.insertTransaction(
            com.example.currencyconverter.domain.model.Transactions(
                transactionFrom = from,
                transactionTo = to,
                fromAmount = fromAmount,
                toAmount = toAmount,
                timestamp = LocalDateTime.now()
            )
        )
    }
}