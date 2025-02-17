import org.example.project.data.ExpenseManager
import org.example.project.data.ExpenseRepositoryImp
import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ExpenseRepositoryTest {

    private val expenseManager = ExpenseManager
    private val repo = ExpenseRepositoryImp(expenseManager)

    @Test
    fun expenseListIsNotEmpty() {

        val expenseList = mutableListOf<Expense>()

        expenseList.addAll(repo.getAllExpenses())

        assertTrue(expenseList.isNotEmpty())
    }

    @Test
    fun addNewExpense() {
        val expenseList = repo.getAllExpenses()

        repo.addExpense(Expense(id = 7, amount = 100.0, category = ExpenseCategory.CAR, description = "Test"))

        assertContains(expenseList, expenseList.find { it.id == 7L })
    }

    @Test
    fun editExpense() {
        val expenseList = repo.getAllExpenses()

        repo.addExpense(Expense(id = 7, amount = 100.0, category = ExpenseCategory.CAR, description = "Test"))

        assertNotNull(expenseList.find { it.id == 7L })

        val updatedExpense = Expense(id = 7, amount = 500.0, category = ExpenseCategory.OTHER, description = "Otros")
        repo.editExpense(updatedExpense)

        val newExpenseList = repo.getAllExpenses()
        assertEquals(updatedExpense, newExpenseList.find { it.id == 7L })

    }

    @Test
    fun getAllCategories() {
        val categories = mutableListOf<ExpenseCategory>()

        categories.addAll(repo.getCategories())

        assertTrue(categories.isNotEmpty())

    }
}