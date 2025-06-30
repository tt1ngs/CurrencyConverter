package com.example.currencyconverter.domain.model

val currencyNames = mapOf(
    "USD" to "Доллар США",
    "EUR" to "Евро",
    "RUB" to "Рубль",
    "GBP" to "Фунт стерлингов",
    "JPY" to "Иена",
    "CNY" to "Юань",
    "AUD" to "Австралийский доллар",
    "CAD" to "Канадский доллар",
    "CHF" to "Швейцарский франк",
    "SEK" to "Шведская крона",
    "NOK" to "Норвежская крона",
    "PLN" to "Польский злотый",
    "BRL" to "Бразильский реал",
    "INR" to "Индийская рупия",
    "ZAR" to "Южноафриканский ранд",
    "TRY" to "Турецкая лира",
    "SGD" to "Сингапурский доллар",
    "HKD" to "Гонконгский доллар",
    "KRW" to "Вона",
    "THB" to "Бат",
    "MXN" to "Мексиканское песо",
    "ILS" to "Новый шекель",
    "CZK" to "Чешская крона",
    "DKK" to "Датская крона",
    "HUF" to "Форинт",
    "MYR" to "Малайзийский ринггит",
    "NZD" to "Новозеландский доллар",
    "PHP" to "Филиппинское песо",
    "RON" to "Румынский лей",
    "BGN" to "Болгарский лев",
    "HRK" to "Хорватская куна",
    "IDR" to "Индонезийская рупия",
    "ISK" to "Исландская крона"
)

fun getCurrencyFullName(code: String): String = currencyNames[code] ?: code

