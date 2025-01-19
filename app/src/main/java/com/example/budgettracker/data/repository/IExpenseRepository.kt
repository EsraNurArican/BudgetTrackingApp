package com.example.budgettracker.data.repository

import androidx.lifecycle.LiveData
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.data.local.model.TYPE

interface IExpenseRepository {
    suspend fun insert(expense: ExpenseEntity)
    suspend fun delete(expense: ExpenseEntity)
    suspend fun getExpensesByType(type: TYPE): List<ExpenseEntity>
    fun getAll(): LiveData<List<ExpenseEntity>>
    suspend fun getTotalByType(type: TYPE): Double
    suspend fun getCategories(): List<String>
    fun getBalance(): LiveData<Double>


}