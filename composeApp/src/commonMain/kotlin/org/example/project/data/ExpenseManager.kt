package org.example.project.data

import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory
import kotlin.math.exp

object ExpenseManager {

    private var currentId = 1L

    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 70.0,
            category = ExpenseCategory.GROCERIES,
            description = "Weekly buy"
        ),
        Expense(
            id = currentId++,
            amount = 50000.0,
            category = ExpenseCategory.CAR,
            description = "Chevrolet"
        ),
        Expense(
            id = currentId++,
            amount = 4.0,
            category = ExpenseCategory.SNACKS,
            description = "Kitkat Chocolate"
        ),
        Expense(
            id = currentId++,
            amount = 23.43,
            category = ExpenseCategory.PARTY,
            description = "weekend Party"
        ),
        Expense(
            id = currentId++,
            amount = 43.44,
            category = ExpenseCategory.HOUSE,
            description = "Cleaning"
        ),
        Expense(
            id = currentId++,
            amount = 15.0,
            category = ExpenseCategory.OTHER,
            description = "weekly buy"
        )
    )

    fun getCategories(): List<ExpenseCategory> {
        return listOf(
            ExpenseCategory.CAR,
            ExpenseCategory.OTHER,
            ExpenseCategory.SNACKS,
            ExpenseCategory.HOUSE,
            ExpenseCategory.COFFEE,
            ExpenseCategory.PARTY
        )
    }

    fun addNewExpense(expense: Expense) {
        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun editExpense(expense: Expense) {
        val index = fakeExpenseList.indexOfFirst {
            it.id == expense.id
        }
        if (index != -1) {
            fakeExpenseList[index] = fakeExpenseList[index].copy(
                amount = expense.amount,
                category = expense.category,
                description = expense.description
            )
        }
    }

    fun deleteExpense(expense: Expense): List<Expense> {
        val index = fakeExpenseList.indexOfFirst {
            it.id == expense.id
        }

        fakeExpenseList.removeAt(index)
        return fakeExpenseList
    }

}