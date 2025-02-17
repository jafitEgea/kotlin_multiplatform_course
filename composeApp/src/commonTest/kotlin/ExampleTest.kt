import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ExampleTest {

    @Test
    fun sumMustSuccess() {
        // Given
        val x = 5
        val y = 10
        // When
        val result = x + y

        // Then
        assertEquals(15, result)
    }

    @Test
    fun sumMustFail() {
        // Given
        val x = 5
        val y = 10
        // When
        val result = x + y

        // Then
        assertNotEquals(10, result)
    }

    @Test
    fun expenseModelListTest() {

        val expenseList = mutableListOf<Expense>()
        val expense =
            Expense(id = 45, amount = 100.0, category = ExpenseCategory.CAR, description = "Test")

        expenseList.add(expense)

        assertContains(expenseList, expense)
    }

    @Test
    fun expenseModelParamTest() {

        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 45, amount = 100.0, category = ExpenseCategory.CAR, description = "Test")
        val expense2 = Expense(id = 43, amount = 140.0, category = ExpenseCategory.HOUSE, description = "Test2")

        expenseList.add(expense)

        assertNotEquals(expense.category, expense2.category)
        assertEquals(expenseList[0].category, expense.category)
    }

}