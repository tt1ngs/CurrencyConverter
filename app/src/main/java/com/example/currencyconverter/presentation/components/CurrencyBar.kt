package com.example.currencyconverter.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.presentation.theme.CurrencyConverterTheme
import com.example.currencyconverter.presentation.theme.Typography
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.currencyconverter.domain.model.getCurrencyFullName

@Composable
fun CurrencyBar(
    currencyName: String,
    currencyCode: String,
    flagUrl: String,
    balance: Double? = null,
    rate: Double? = null,
    isSelected: Boolean = false,
    isInputMode: Boolean = false,
    inputValue: String = "1",
    onInputChange: (String) -> Unit = {},
    onClearInput: () -> Unit = {},
    onClick: () -> Unit = {},
    onEnterInputMode: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var imageLoading by remember { mutableStateOf(true) }
    // Маппинг кода валюты в человекочитаемое название
    val currencyFullName = getCurrencyFullName(currencyCode)
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .width(332.dp)
            .height(IntrinsicSize.Min)
            .clip(MaterialTheme.shapes.large)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                else MaterialTheme.colorScheme.secondary
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = flagUrl,
                    onSuccess = { imageLoading = false },
                    onError = { imageLoading = false }
                ),
                contentDescription = "$currencyCode flag",
                modifier = Modifier
                    .matchParentSize(),
                contentScale = ContentScale.Crop
            )
            if (imageLoading) {
                Icon(
                    imageVector = Icons.Default.Close, // fallback icon
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.matchParentSize()
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Код валюты (фиксированное поле)
            Text(
                text = currencyCode,
                style = Typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
            // Название валюты (фиксированное поле)
            Text(
                text = currencyFullName,
                style = Typography.bodySmall,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
            // Баланс (фиксированное поле)
            if (balance != null) {
                Text(
                    text = "Баланс: %.2f".format(balance),
                    style = Typography.bodySmall,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                )
            }
            // Ввод суммы (если режим ввода)
            if (isInputMode) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = inputValue,
                        onValueChange = { newValue ->
                            if (newValue.matches(Regex("^\\d*\\.?\\d*"))) onInputChange(newValue)
                        },
                        singleLine = true,
                        textStyle = Typography.bodyMedium.copy(
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
                            textAlign = TextAlign.Start
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .width(70.dp)
                    )
                    if (inputValue != "1") {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(18.dp)
                                .clickable {
                                    onClearInput()
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                        )
                    }
                }
            } else if (isSelected) {
                // Если не режим ввода, но элемент выбран — клик по сумме активирует режим ввода
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = inputValue,
                    style = Typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .width(70.dp)
                        .clickable { onEnterInputMode() }
                )
            }
        }
        // Фиксированное поле для стоимости валюты
        Box(
            modifier = Modifier
                .width(80.dp)
                .padding(end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (rate != null) {
                Text(
                    text = "%.4f".format(rate),
                    style = Typography.bodyMedium,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrencyBarPreview() {
    CurrencyConverterTheme {
        Column {
            CurrencyBar(
                currencyName = "Евро",
                currencyCode = "EUR",
                flagUrl = "https://www.xe.com/svgs/flags/eur.static.svg",
                balance = 1234.56,
                rate = 1.2345,
                isSelected = true,
                isInputMode = false,
                onClick = {}
            )
            CurrencyBar(
                currencyName = "Евро",
                currencyCode = "EUR",
                flagUrl = "https://www.xe.com/svgs/flags/eur.static.svg",
                balance = 1234.56,
                rate = 1.2345,
                isSelected = true,
                isInputMode = true,
                inputValue = "150",
                onInputChange = {},
                onClearInput = {},
                onClick = {}
            )
        }
    }
}
