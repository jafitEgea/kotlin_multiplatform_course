package org.example.project.data

import com.expenseApp.db.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory
import org.example.project.domain.ExpenseRepository
import org.example.project.model.NetworkExpense

private const val BASE_URL = "http://192.168.0.102:8080"

class ExpenseRepositoryImp(
//    private val expenseManager: ExpenseManager,
    private val appDatabase: AppDatabase, private val httpClient: HttpClient
) : ExpenseRepository {

    private val queries = appDatabase.expensesDbQueries

    override suspend fun getAllExpenses(): List<Expense> {
        // Pasos
        // 1. Buscar info al server
        // 2. guardar info en mi db local
        // 3. usar info local de mi db local

        return if (queries.selectAll().executeAsList().isEmpty()) {
            val networkResponse = httpClient.get("$BASE_URL/expenses").body<List<NetworkExpense>>()

            if (networkResponse.isEmpty()) emptyList<Expense>()

            val expenses = networkResponse.map { networkExpense ->
                Expense(
                    id = networkExpense.id,
                    amount = networkExpense.amount,
                    category = ExpenseCategory.valueOf(networkExpense.categoryName),
                    description = networkExpense.description
                )
            }

            expenses.forEach {
                queries.insert(it.amount, it.category.name, it.description)
            }
            // return
            expenses
        } else {
            queries.selectAll().executeAsList().map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.categoryName),
                    description = it.description
                )
            }
        }
//        return expenseManager.fakeExpenseList
    }

    override suspend fun addExpense(expense: Expense) {
        queries.transaction {
            queries.insert(
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
//        expenseManager.addNewExpense(expense)

        httpClient.post("$BASE_URL/expenses") {
            contentType(ContentType.Application.Json)
            setBody(
                NetworkExpense(
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description
                )
            )
        }
    }

    override suspend fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
//        expenseManager.editExpense(expense)

        httpClient.put("$BASE_URL/expenses/${expense.id}") {
            contentType(ContentType.Application.Json)
            setBody(
                NetworkExpense(
                    id = expense.id,
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description
                )
            )
        }
    }

    override fun getCategories(): List<ExpenseCategory> {
        return queries.categories().executeAsList().map {
            ExpenseCategory.valueOf(it)
        }
//        return expenseManager.getCategories()
    }

    override suspend fun deleteExpense(id: Long) {

        httpClient.delete("$BASE_URL/expenses/${id}")

        /*httpClient.delete("$BASE_URL/expenses/${id}") {
            contentType(ContentType.Application.Json)
            setBody(
                NetworkExpense(
                    id = expense.id,
                    amount = expense.amount,
                    categoryName = expense.category.name,
                    description = expense.description
                )
            )
        }*/
        queries.transaction {
            queries.delete(id)
        }
//        return expenseManager.deleteExpense(expense)
    }
}