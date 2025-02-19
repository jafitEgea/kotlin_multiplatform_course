package org.example.project.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.model.Expense
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.example.project.domain.ExpenseRepository
import org.example.project.model.ExpenseCategory

data class ExpenseUiState(
    val expenses: List<Expense> = emptyList(), val total: Double = 0.0
)

class ExpensesViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState())

    val uiState = _uiState.asStateFlow()

    private var allExpense: MutableList<Expense> = mutableListOf()

    init {
        getAllExpenses()
    }

    private fun updateExpenseList() {
        viewModelScope.launch {
            allExpense = repository.getAllExpenses().toMutableList()
            updateState()
        }
    }

    private fun getAllExpenses() {
        repository.getAllExpenses()
        updateExpenseList()
    }

    fun addExpense(expense: Expense) {
        repository.addExpense(expense)
        updateExpenseList()
    }

    fun editExpense(expense: Expense) {
        repository.editExpense(expense)
        updateExpenseList()
    }

    fun deleteExpense(expense: Expense) {
        repository.deleteExpense(expense)
        updateExpenseList()
    }

    fun getExpenseWithId(id: Long): Expense {
        return allExpense.first { it.id == id }
    }

    fun getCategories(): List<ExpenseCategory> {
        return repository.getCategories()
    }


    private fun updateState() {
        _uiState.update { state ->
            state.copy(expenses = allExpense, total = allExpense.sumOf { it.amount })
        }
    }
}