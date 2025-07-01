package com.example.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.presentation.currency.CurrencyScreen
import com.example.currencyconverter.presentation.exchange.ExchangeScreen
import com.example.currencyconverter.presentation.transactions.TransactionsScreen
import com.example.currencyconverter.presentation.theme.CurrencyConverterTheme
import com.example.currencyconverter.presentation.viewmodel.CurrencyViewModel
import com.example.currencyconverter.presentation.viewmodel.ExchangeViewModel
import com.example.currencyconverter.presentation.viewmodel.TransactionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "currencies") {
        composable("currencies") {
            val viewModel: CurrencyViewModel = hiltViewModel()
            val currencies by viewModel.currencies.collectAsState()
            val selectedCurrency by viewModel.selectedCurrency.collectAsState()
            val isInputMode by viewModel.isInputMode.collectAsState()
            val inputValue by viewModel.inputValue.collectAsState()
            val exchangeViewModel: ExchangeViewModel = hiltViewModel()
            val isSuccess by exchangeViewModel.isSuccess.collectAsState()
            LaunchedEffect(isSuccess) {
                if (isSuccess) {
                    // После успешной покупки обновить курсы и балансы
                    selectedCurrency?.let {
                        viewModel.startRates(it.currency, inputValue.toDoubleOrNull() ?: 1.0)
                    }
                    exchangeViewModel.resetSuccess()
                }
            }
            CurrencyScreen(
                currencies = currencies,
                selectedCurrency = selectedCurrency,
                isInputMode = isInputMode,
                inputValue = inputValue,
                onInputChange = viewModel::setInputValue,
                onClearInput = viewModel::clearInput,
                onCurrencyClick = { clickedCurrency ->
                    if (isInputMode && selectedCurrency != null) {
                        val selected = selectedCurrency
                        Log.d("CurrencyNav", "isInputMode: $isInputMode, selected: $selected, clicked: $clickedCurrency, inputValue: $inputValue")
                        if (clickedCurrency.currency == selected?.currency) {
                            val fromCurrency = currencies.firstOrNull {
                                it.currency != selected.currency && it.balance >= (inputValue.toDoubleOrNull() ?: 1.0) * it.value
                            } ?: currencies.firstOrNull { it.currency != selected.currency }
                            Log.d("CurrencyNav", "Clicked selected. fromCurrency: $fromCurrency")
                            if (fromCurrency != null) {
                                navController.navigate("exchange/${fromCurrency.currency.name}/${selected.currency.name}")
                                Log.d("CurrencyNav", "Navigate to exchange/${fromCurrency.currency.name}/${selected.currency.name}")
                            } else {
                                Log.d("CurrencyNav", "No suitable fromCurrency found")
                            }
                        } else {
                            Log.d("CurrencyNav", "Clicked other. Navigate to exchange/${clickedCurrency.currency.name}/${selected?.currency?.name}")
                            navController.navigate("exchange/${clickedCurrency.currency.name}/${selected?.currency?.name}")
                        }
                    } else {
                        Log.d("CurrencyNav", "Select currency: $clickedCurrency")
                        viewModel.selectCurrency(clickedCurrency)
                    }
                },
                onEnterInputMode = { viewModel.enterInputMode() },
                onTransactionClick = { navController.navigate("transactions") }
            )
        }
        composable(
            "exchange/{fromCode}/{toCode}",
        ) { backStackEntry ->
            val currencyViewModel: CurrencyViewModel = hiltViewModel()
            val exchangeViewModel: ExchangeViewModel = hiltViewModel()
            val currencies by currencyViewModel.currencies.collectAsState()
            val inputValue by currencyViewModel.inputValue.collectAsState()
            val fromCode = backStackEntry.arguments?.getString("fromCode")
            val toCode = backStackEntry.arguments?.getString("toCode")
            val fromCurrency = currencies.find { it.currency.name == fromCode }
            val toCurrency = currencies.find { it.currency.name == toCode }
            if (fromCurrency != null && toCurrency != null) {
                var localFromAmount by remember { mutableStateOf(inputValue) }
                val rate = fromCurrency.value
                val toAmount = String.format("%.2f", localFromAmount.toDoubleOrNull()?.times(rate) ?: 0.0)
                val isSuccess by exchangeViewModel.isSuccess.collectAsState()
                if (isSuccess) {
                    LaunchedEffect(Unit) {
                        navController.popBackStack("currencies", inclusive = false)
                    }
                } else {
                    ExchangeScreen(
                        fromCurrency = fromCurrency,
                        toCurrency = toCurrency,
                        fromAmount = localFromAmount,
                        toAmount = toAmount,
                        rate = rate,
                        onBuyClick = {
                            exchangeViewModel.buy(
                                fromCurrency = fromCurrency,
                                toCurrency = toCurrency,
                                fromAmount = localFromAmount.toDoubleOrNull() ?: 0.0,
                                toAmount = toAmount.toDoubleOrNull() ?: 0.0
                            )
                        },
                        onBack = { navController.popBackStack() },
                        onFromAmountChange = {
                            localFromAmount = it
                        }
                    )
                }
            }
        }
        composable("transactions") {
            val viewModel: TransactionsViewModel = hiltViewModel()
            val transactions by viewModel.transactions.collectAsState()
            TransactionsScreen(transactions = transactions, onBack = { navController.popBackStack() })
        }
    }
}