package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.CurrencyInfo
import com.example.currencyconverter.domain.usecase.BuyCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val buyCurrency: BuyCurrency
) : ViewModel() {
    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    fun buy(
        fromCurrency: CurrencyInfo,
        toCurrency: CurrencyInfo,
        fromAmount: Double,
        toAmount: Double
    ) {
        viewModelScope.launch {
            try {
                buyCurrency(
                    from = fromCurrency.currency,
                    to = toCurrency.currency,
                    fromAmount = fromAmount,
                    toAmount = toAmount
                )
                _isSuccess.value = true
            } catch (e: Exception) {
                _isSuccess.value = false
            }
        }
    }
}

