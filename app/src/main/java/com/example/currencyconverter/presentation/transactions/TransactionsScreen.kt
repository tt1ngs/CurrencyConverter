package com.example.currencyconverter.presentation.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.domain.model.Transactions
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    transactions: List<Transactions> = listOf(),
    onBack: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            },
            title = {
                Text(
                    text = "Транзакции",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(transactions) { tx ->
                TransactionItem(tx)
            }
        }
    }
}

@Composable
fun TransactionItem(tx: Transactions) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = "${tx.fromAmount} ${tx.transactionFrom} → ${tx.toAmount} ${tx.transactionTo}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = tx.timestamp.format(formatter),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}
