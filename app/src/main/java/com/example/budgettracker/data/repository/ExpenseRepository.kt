package com.example.budgettracker.data.repository

import androidx.lifecycle.LiveData
import com.example.budgettracker.data.local.dao.ExpenseDao
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.data.local.model.TYPE
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao): IExpenseRepository {
    override suspend fun insert(expense: ExpenseEntity) = expenseDao.insert(expense)

    override suspend fun delete(expense: ExpenseEntity) = expenseDao.delete(expense)

    override fun getAll() = expenseDao.getAll()

    override suspend fun getExpensesByType(type: TYPE): List<ExpenseEntity> = expenseDao.getExpensesByType(type)

    override suspend fun getTotalByType(type: TYPE):Double = expenseDao.getTotalByType(type)

    override suspend fun getCategories(): List<String> = expenseDao.getCategories()

    override fun getBalance():LiveData<Double> = expenseDao.getBalance(TYPE.INCOME,TYPE.EXPENSE)
}