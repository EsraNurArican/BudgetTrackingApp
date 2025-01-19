package com.example.budgettracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.data.local.model.TYPE
import com.example.budgettracker.data.repository.ExpenseRepository
import com.example.budgettracker.data.repository.IExpenseRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel  @Inject constructor(private val expenseRepository: IExpenseRepository) : ViewModel(){

    // get the expenses and total balance as live directly from database
    // since db return live data, changes will seen on the ui immediately
    val expenses: LiveData<List<ExpenseEntity>> = expenseRepository.getAll()
    val totalBalance: LiveData<Double> = expenseRepository.getBalance()

}


