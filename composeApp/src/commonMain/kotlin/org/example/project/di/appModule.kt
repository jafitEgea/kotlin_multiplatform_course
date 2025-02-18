package org.example.project.di

import org.example.project.data.ExpenseManager
import org.example.project.data.ExpenseRepositoryImp
import org.example.project.domain.ExpenseRepository
import org.example.project.presentation.ExpensesViewModel
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun appModule() = module {
    // Se crea la instancia
    single { ExpenseManager }.withOptions { createdAtStart() }
    // Se crea el repositorio llamando la instancia creada anteriormente get()
    single<ExpenseRepository> { ExpenseRepositoryImp(get()) }
    factory { ExpensesViewModel(get()) }
}