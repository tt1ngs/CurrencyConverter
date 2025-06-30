package com.example.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.model.Transactions
import com.example.currencyconverter.domain.usecase.GetTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactions: GetTransactions
) : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transactions>>(emptyList())
    val transactions: StateFlow<List<Transactions>> = _transactions.asStateFlow()

    init {
        viewModelScope.launch {
            getTransactions.getAllFlow().collectLatest {
                _transactions.value = it
            }
        }
    }
}

