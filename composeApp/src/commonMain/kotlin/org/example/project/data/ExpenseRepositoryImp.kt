package org.example.project.data

import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory
import org.example.project.domain.ExpenseRepository

class ExpenseRepositoryImp(private val expenseManager: ExpenseManager): ExpenseRepository {
    override fun getAllExpenses(): List<Expense> {
        return expenseManager.fakeExpenseList
    }

    override fun addExpense(expense: Expense) {
        expenseManager.addNewExpense(expense)
    }

    override fun editExpense(expense: Expense) {
        expenseManager.editExpense(expense)
    }

    override fun getCategories(): List<ExpenseCategory> {
        return expenseManager.getCategories()
    }

    override fun deleteExpense(expense: Expense): List<Expense> {
        return expenseManager.deleteExpense(expense)
    }
}