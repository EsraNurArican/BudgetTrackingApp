package com.example.budgettracker.di

import android.content.Context
import androidx.room.Room
import com.example.budgettracker.data.local.dao.ExpenseDao
import com.example.budgettracker.data.local.database.ExpenseDatabase
import com.example.budgettracker.data.repository.ExpenseRepository
import com.example.budgettracker.data.repository.IExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase{
        return ExpenseDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideExpensesDao(database: ExpenseDatabase): ExpenseDao{
        return database.expenseDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao) : IExpenseRepository {
        return ExpenseRepository(expenseDao)
    }
}