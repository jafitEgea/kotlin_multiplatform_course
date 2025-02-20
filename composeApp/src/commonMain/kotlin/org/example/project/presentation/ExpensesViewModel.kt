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

/*data class ExpenseUiState(
    val expenses: List<Expense> = emptyList(), val total: Double = 0.0
)*/

// Sealed class permite tener subclases del mismo tipo. Se usa object en Loading porque no se necesita retornar algo
sealed class ExpenseUiState {
    object Loading : ExpenseUiState()
    data class Success(val expenses: List<Expense>, val total: Double) : ExpenseUiState()
    data class Error(val message: String) : ExpenseUiState()
}

class ExpensesViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpenseUiState>(ExpenseUiState.Loading)

    val uiState = _uiState.asStateFlow()

//    private var allExpense: MutableList<Expense> = mutableListOf()

    init {
        getExpenseList()
    }

    private fun getExpenseList() {
        // Se puede mejorar para que traiga el listado actualizado cada cierto tiempo
        viewModelScope.launch {
            try {
                val expenses = repository.getAllExpenses()
                _uiState.value = ExpenseUiState.Success(expenses, expenses.sumOf { it.amount })
            } catch (e: Exception) {
                _uiState.value = ExpenseUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    private suspend fun updateExpenseList() {
        try {
            val expenses = repository.getAllExpenses()
            _uiState.value = ExpenseUiState.Success(expenses, expenses.sumOf { it.amount })
        } catch (e: Exception) {
            _uiState.value = ExpenseUiState.Error(e.message ?: "Ocurrió un error")
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repository.addExpense(expense)
                updateExpenseList()
            } catch (e: Exception) {
                _uiState.value = ExpenseUiState.Error(e.message ?: "Ocurrió un error")

            }
        }
    }

    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repository.editExpense(expense)
                updateExpenseList()
            } catch (e: Exception) {
                _uiState.value = ExpenseUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    fun deleteExpense(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteExpense(id)
                updateExpenseList()
            } catch (e: Exception) {
                _uiState.value = ExpenseUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    fun getExpenseWithId(id: Long): Expense? {
        return (_uiState.value as? ExpenseUiState.Success)?.expenses?.firstOrNull { it.id == id }
    }

    fun getCategories(): List<ExpenseCategory> {
        return repository.getCategories()
    }
}