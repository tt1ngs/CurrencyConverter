package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.domain.model.Currency
import com.example.currencyconverter.domain.usecase.GetRatesFlow
import com.example.currencyconverter.domain.usecase.GetBalances
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getRatesFlow: GetRatesFlow,
    private val getBalances: GetBalances
) : ViewModel() {
    private val _currencies = MutableStateFlow<List<CurrencyInfo>>(emptyList())
    val currencies: StateFlow<List<CurrencyInfo>> = _currencies.asStateFlow()

    private val _selectedCurrency = MutableStateFlow<CurrencyInfo?>(null)
    val selectedCurrency: StateFlow<CurrencyInfo?> = _selectedCurrency.asStateFlow()

    private val _isInputMode = MutableStateFlow(false)
    val isInputMode: StateFlow<Boolean> = _isInputMode.asStateFlow()

    private val _inputValue = MutableStateFlow("1")
    val inputValue: StateFlow<String> = _inputValue.asStateFlow()

    init {
        startRates(Currency.USD, 1.0)
    }

    fun startRates(base: Currency, amount: Double) {
        viewModelScope.launch {
            getRatesFlow(base, amount).collectLatest { list ->
                _currencies.value = list
                if (_selectedCurrency.value == null) {
                    _selectedCurrency.value = list.firstOrNull()
                }
            }
        }
    }

    fun selectCurrency(currency: CurrencyInfo) {
        _selectedCurrency.value = currency
        _isInputMode.value = false
        _inputValue.value = "1"
        startRates(currency.currency, 1.0)
    }

    fun enterInputMode() {
        _isInputMode.value = true
    }

    fun setInputValue(value: String) {
        _inputValue.value = value
        _selectedCurrency.value?.let {
            startRates(it.currency, value.toDoubleOrNull() ?: 1.0)
        }
    }

    fun clearInput() {
        _inputValue.value = "1"
        _isInputMode.value = false
        _selectedCurrency.value?.let {
            startRates(it.currency, 1.0)
        }
    }
}

