package com.example.currencyconverter.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa)),
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp, //32
        lineHeight = 38.sp, //38
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa)),
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp, //48
        lineHeight = 50.sp, //56
        letterSpacing = 0.75.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa)),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, //20
        lineHeight = 24.sp, //24
        letterSpacing = 0.25.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa)),
        fontWeight = FontWeight.Normal,
        fontSize = 7.sp, //7
        lineHeight = 9.sp, //9
        letterSpacing = 0.25.sp

    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa)),
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp, // 15
        lineHeight = 19.sp, // 19
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa)),
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.25.sp
    ),
)