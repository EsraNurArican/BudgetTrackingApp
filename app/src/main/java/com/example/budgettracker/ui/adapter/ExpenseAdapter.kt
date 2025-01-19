package com.example.budgettracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.data.local.model.ExpenseEntity
import com.example.budgettracker.databinding.ItemExpenseBinding

class ExpenseAdapter: RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    private var expenseList: List<ExpenseEntity> = emptyList()

    fun setExpenses(expenses: List<ExpenseEntity>) {
        expenseList = expenses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenseList[position])
    }

    override fun getItemCount(): Int = expenseList.size

    class ExpenseViewHolder(private val binding: ItemExpenseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: ExpenseEntity) {
            binding.expense = expense
            binding.executePendingBindings()
        }
    }
}