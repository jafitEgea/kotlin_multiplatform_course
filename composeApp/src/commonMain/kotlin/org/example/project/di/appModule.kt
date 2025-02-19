package org.example.project.di

import com.expenseApp.db.AppDatabase
import org.example.project.data.ExpenseRepositoryImp
import org.example.project.domain.ExpenseRepository
import org.example.project.presentation.ExpensesViewModel
import org.koin.dsl.module

fun appModule(appDatabase: AppDatabase) = module {
    // Se crea la instancia
//    single { ExpenseManager }.withOptions { createdAtStart() }
    // Se crea el repositorio llamando la instancia creada anteriormente get()
    single<ExpenseRepository> { ExpenseRepositoryImp(appDatabase) }
    factory { ExpensesViewModel(get()) }
}