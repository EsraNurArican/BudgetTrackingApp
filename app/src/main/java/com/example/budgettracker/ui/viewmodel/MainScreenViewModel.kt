package com.example.budgettracker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.data.repository.IExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel  @Inject constructor(private val expenseRepository: IExpenseRepository) : ViewModel(){

    // get the expenses and total balance as live directly from database
    // since db return live data, changes will seen on the ui immediately
    val expenses: LiveData<List<ExpenseEntity>> = expenseRepository.getAll()
    val totalBalance: LiveData<Double> = expenseRepository.getBalance()

}