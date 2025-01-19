package com.example.budgettracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val category: String,
    val date: Long,
    val type: TYPE
)


enum class TYPE {
    INCOME,
    EXPENSE
}
