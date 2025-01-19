package com.example.budgettracker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.data.local.model.TYPE
import com.example.budgettracker.data.repository.IExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
private const val TAG = "AddExpenseViewModel"

@HiltViewModel
class AddExpenseViewModel @Inject constructor(private val expenseRepository: IExpenseRepository) : ViewModel() {
    val amount = MutableLiveData<String>()
    val selectedDate = MutableLiveData<Long>()
    val selectedCategory = MutableLiveData<String>()
    val categoryList = MutableLiveData<List<String>>()
    private val typeList = MutableLiveData<List<String>>() // Turkish type strings
    private val selectedType = MutableLiveData<TYPE>()    // TYPE enum
    val selectedTypeString = MutableLiveData<String>() // Turkish string for UI binding
    val formattedDate = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String?>()


    private val typeTranslationMap = mapOf(
        TYPE.INCOME to "Gelir",
        TYPE.EXPENSE to "Gider"
    )
    private val reverseTypeTranslationMap = typeTranslationMap.entries.associate { (k, v) -> v to k }

    init {
        loadCategories()
        loadTypeList()
        setDefaultDate()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val categories = expenseRepository.getCategories()
            categoryList.value = categories // Load categories from DB
            Log.d(TAG, categories.toString())
        }
    }

    private fun loadTypeList() {
        typeList.value = typeTranslationMap.values.toList() // Populate Turkish type list
    }

    private fun setDefaultDate() {
        selectedDate.value = System.currentTimeMillis() // Default to current date
    }

    fun onDateSelected(dateInMillis: Long) {
        selectedDate.value = dateInMillis
        updateFormattedDate(dateInMillis)
    }

    fun onTypeSelected(turkishType: String) {
        selectedTypeString.value = turkishType // to show ui directly
        selectedType.value = reverseTypeTranslationMap[turkishType]
    }

    private fun updateFormattedDate(dateInMillis: Long) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formattedDate.value = dateFormat.format(Date(dateInMillis))
    }

    fun saveExpense(): Boolean {

        // validate inputs
        val amountValue = amount.value?.toDoubleOrNull()
        if (amountValue == null || selectedCategory.value.isNullOrEmpty() || selectedType.value == null) {
           errorMessage.value = "Tüm alanları doldurun."
            return false
        }

        errorMessage.value = null

        // save the entity
        val expense = ExpenseEntity(
            amount = amount.value?.toDoubleOrNull() ?: 0.0,
            category = selectedCategory.value.orEmpty(),
            type = selectedType.value ?: TYPE.INCOME,
            date = selectedDate.value ?: System.currentTimeMillis()
        )
        Log.d(TAG, expense.toString())
        viewModelScope.launch {
            expenseRepository.insert(expense)
        }
        return true
    }
}