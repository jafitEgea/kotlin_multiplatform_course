package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.ExpenseManager
import org.example.project.presentation.ExpenseUiState
import org.example.project.ui.AllExpensesHeader
import org.example.project.ui.ExpensesItem
import org.example.project.ui.ExpensesScreen
import org.example.project.ui.ExpensesTotalHeader

@Preview(showBackground = true)
@Composable
private fun ExpensesTotalHeaderPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        ExpensesTotalHeader(total = 1028.0)
    }
}

@Preview(showBackground = true)
@Composable
private fun AllExpensesHeaderPreview() {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        AllExpensesHeader()
    }
}

@Preview
@Composable
private fun ExpensesItemPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        ExpensesItem(expense = ExpenseManager.fakeExpenseList[1], onExpenseClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseScreenPreview() {
    ExpensesScreen(
        uiState = ExpenseUiState(
            expenses = ExpenseManager.fakeExpenseList,
            total = 1052.32
        ), onExpenseClick = {}
    )
}