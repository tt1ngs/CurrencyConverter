package com.example.currencyconverter.presentation.exchange

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.presentation.components.Button
import com.example.currencyconverter.presentation.components.CurrencyBar
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeScreen(
    fromCurrency: CurrencyInfo,
    toCurrency: CurrencyInfo,
    fromAmount: String,
    toAmount: String,
    rate: Double,
    onBuyClick: () -> Unit,
    onBack: () -> Unit = {},
    onFromAmountChange: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Обмен валюты",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        CurrencyBar(
            currencyName = fromCurrency.name,
            currencyCode = fromCurrency.currency.name,
            flagUrl = fromCurrency.flagUrl,
            balance = fromCurrency.balance,
            rate = null,
            isSelected = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Поле для ввода суммы, которую отдаём
        BasicTextField(
            value = fromAmount,
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*"))) onFromAmountChange(it)
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 4.dp)
        )
        Text(
            text = "→",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        CurrencyBar(
            currencyName = toCurrency.name,
            currencyCode = toCurrency.currency.name,
            flagUrl = toCurrency.flagUrl,
            balance = toCurrency.balance,
            rate = null,
            isSelected = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Показываем сколько получим
        Text(
            text = "Получите: $toAmount",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Курс: %.4f".format(rate),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onBuyClick,
            text = "Купить"
        )
    }
}
