package com.example.currencyconverter.presentation.currency

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.presentation.components.CurrencyBar
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    currencies: List<CurrencyInfo> = listOf(),
    selectedCurrency: CurrencyInfo? = null,
    isInputMode: Boolean = false,
    inputValue: String = "1",
    onInputChange: (String) -> Unit = {},
    onClearInput: () -> Unit = {},
    onCurrencyClick: (CurrencyInfo) -> Unit = {},
    onEnterInputMode: () -> Unit = {},
    onTransactionClick: () -> Unit = {}
) {
    var localInputMode by remember { mutableStateOf(isInputMode) }
    var localInputValue by remember { mutableStateOf(inputValue) }

    // Обработка кнопки "назад" для выхода из режима ввода
    BackHandler(enabled = localInputMode) {
        localInputMode = false
        localInputValue = "1"
        onClearInput()
    }

    val sortedCurrencies = remember(currencies, selectedCurrency) {
        if (selectedCurrency != null) {
            val selected = currencies.find { it.currency == selectedCurrency.currency }
            val others = currencies.filter { it.currency != selectedCurrency.currency }
            listOfNotNull(selected) + others
        } else {
            currencies
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Валюты",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            actions = {
                IconButton(onClick = onTransactionClick) {
                    Icon(
                        imageVector = Icons.TwoTone.AddCircle,
                        contentDescription = "Транзакции",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(sortedCurrencies, key = { it.currency.name }) { currency ->
                CurrencyBar(
                    currencyName = currency.name,
                    currencyCode = currency.currency.name,
                    flagUrl = currency.flagUrl,
                    balance = currency.balance,
                    rate = currency.value,
                    isSelected = selectedCurrency?.currency == currency.currency,
                    isInputMode = localInputMode && selectedCurrency?.currency == currency.currency,
                    inputValue = if (localInputMode && selectedCurrency?.currency == currency.currency) localInputValue else "1",
                    onInputChange = {
                        localInputValue = it
                        onInputChange(it)
                    },
                    onClearInput = {
                        localInputValue = "1"
                        onClearInput()
                    },
                    onClick = {
                        onCurrencyClick(currency)
                    },
                    onEnterInputMode = {
                        localInputMode = true
                        localInputValue = localInputValue
                        onEnterInputMode()
                    }
                )
            }
        }
    }
}
