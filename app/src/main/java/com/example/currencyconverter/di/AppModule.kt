package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.local.dao.AccountDao
import com.example.currencyconverter.data.local.dao.TransactionDao
import com.example.currencyconverter.data.local.db.ConverterDatabase
import com.example.currencyconverter.data.remote.RatesService
import com.example.currencyconverter.data.remote.impl.RemoteRatesServiceImpl
import com.example.currencyconverter.data.repository.impl.AccountsRepositoryImpl
import com.example.currencyconverter.data.repository.impl.RatesRepositoryImpl
import com.example.currencyconverter.data.repository.impl.TransactionsRepositoryImpl
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.domain.repository.RatesRepository
import com.example.currencyconverter.domain.repository.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ConverterDatabase =
        Room.databaseBuilder(
            context,
            ConverterDatabase::class.java,
            "converter.db"
        )
        .build()

    @Provides
    fun provideAccountDao(db: ConverterDatabase): AccountDao = db.accountDao()

    @Provides
    fun provideTransactionDao(db: ConverterDatabase): TransactionDao = db.transactionDao()

    @Provides
    @Singleton
    fun provideRatesService(): RatesService = RemoteRatesServiceImpl()

    @Provides
    @Singleton
    fun provideRatesRepository(
        ratesService: RatesService,
        accountDao: AccountDao
    ): RatesRepository = RatesRepositoryImpl(ratesService, accountDao)

    @Provides
    @Singleton
    fun provideAccountRepository(accountDao: AccountDao): AccountRepository =
        AccountsRepositoryImpl(accountDao)

    @Provides
    @Singleton
    fun provideTransactionsRepository(transactionDao: TransactionDao): TransactionsRepository =
        TransactionsRepositoryImpl(transactionDao)
}
