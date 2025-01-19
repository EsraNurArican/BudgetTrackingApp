package com.example.budgettracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.data.local.model.TYPE

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAll(): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE type = :type ORDER BY date DESC")
    suspend fun getExpensesByType(type: TYPE): List<ExpenseEntity>

    @Query("SELECT SUM(amount) FROM expenses WHERE type = :type")
    suspend fun getTotalByType(type: TYPE): Double

    @Query("SELECT DISTINCT category FROM expenses")
    suspend fun getCategories(): List<String>

    @Query("SELECT COALESCE((SELECT SUM(amount) FROM expenses WHERE type = :incomeType), 0) - COALESCE((SELECT SUM(amount) FROM expenses WHERE type = :expenseType), 0)")
    fun getBalance(incomeType: TYPE, expenseType: TYPE): LiveData<Double>

}