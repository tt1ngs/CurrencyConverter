package com.example.currencyconverter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.currencyconverter.data.local.converter.Converters
import java.time.LocalDateTime

@Entity(tableName = "transactions")
@TypeConverters(Converters::class)
data class TransactionDbo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "currency_code_from")
    val from: String,
    @ColumnInfo(name = "currency_code_to")
    val to: String,
    @ColumnInfo(name = "amount_from")
    val fromAmount: Double,
    @ColumnInfo(name = "amount_to")
    val toAmount: Double,
    val dateTime: LocalDateTime,
)