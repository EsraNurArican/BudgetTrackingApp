package com.example.budgettracker.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.budgettracker.R
import com.example.budgettracker.databinding.FragmentAddExpenseBinding
import com.example.budgettracker.ui.viewmodel.AddExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddExpenseFragment : DialogFragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        binding.expenseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup UI components
        setupSpinners()
        setupDatePicker()

        // Save button listener
        binding.saveButton.setOnClickListener {
            if (viewModel.saveExpense()) {
                dismiss()   // Close dialog on success
            } else {
                viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                    message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(R.drawable.app_background_drawable)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return dialog
    }

    private fun setupSpinners() {
        // Setup Type Spinner
        val typeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOf("Gelir", "Gider")
        )
        binding.typeSpinner.apply {
            setAdapter(typeAdapter)
            setOnFocusChangeListener { _, _ -> showDropDown() }
            setOnItemClickListener { _, _, position, _ ->
                val selectedType = typeAdapter.getItem(position)
                selectedType?.let { viewModel.onTypeSelected(it) }
            }
        }

        // Setup Category Spinner
        viewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            val categoryAdapter =
                ArrayAdapter(requireContext(), R.layout.spinner_item_layout, categories)
            binding.categorySpinner.apply {
                setAdapter(categoryAdapter)
                setOnFocusChangeListener { _, _ -> showDropDown() }
                setOnItemClickListener { _, _, position, _ ->
                    categoryAdapter.getItem(position)?.let { viewModel.selectedCategory.value = it }
                }
                setOnEditorActionListener { _, _, _ ->
                    val newCategory = binding.categorySpinner.text.toString()
                    if (newCategory.isNotEmpty()) {
                        viewModel.selectedCategory.value = newCategory
                    }
                    false
                }
            }
        }
    }

    private fun setupDatePicker() {
        binding.dateField.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = viewModel.selectedDate.value ?: System.currentTimeMillis()
            }
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val selectedDate =
                        Calendar.getInstance().apply { set(year, month, day) }.timeInMillis
                    viewModel.onDateSelected(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}