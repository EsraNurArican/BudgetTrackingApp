package com.example.budgettracker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgettracker.R
import com.example.budgettracker.databinding.FragmentMainScreenBinding
import com.example.budgettracker.ui.adapter.ExpenseAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG: String = "MainScreenFragment"

@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainScreenViewModel by viewModels()
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseAdapter = ExpenseAdapter()
        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expenseAdapter
        }

        // Set expense adapter to show all expense list items on the RV
        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            Log.d(TAG,expenses.toString())
            expenseAdapter.setExpenses(expenses)
        }

        // Open dialog fragment to add new entity
        // Used dialog fragment to show main screen shows changes directly
        binding.buttonAdd.setOnClickListener{
            AddExpenseFragment().show(childFragmentManager, "AddExpenseDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}