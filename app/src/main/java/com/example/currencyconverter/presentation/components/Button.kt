package com.example.currencyconverter.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.presentation.theme.CurrencyConverterTheme
import com.example.currencyconverter.presentation.theme.Typography

@Composable
fun Button(
    onClick: () -> Unit,
    text: String,
    topPadding: Int = 0,
    bottomPadding: Int = 0
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(223.dp)
            .padding(top = topPadding.dp, bottom = bottomPadding.dp),
        shape = MaterialTheme.shapes.large,
        content = {
            Text(
                text = text,
                style = Typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
fun ButtonPreview() {
    CurrencyConverterTheme {

        Button(
            onClick = { /* Do something */ },
            text = "Click Me",
            topPadding = 16,
            bottomPadding = 16
        )
    }
}