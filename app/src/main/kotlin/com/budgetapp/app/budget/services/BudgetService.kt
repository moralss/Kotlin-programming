package com.budgetapp.app.budget.services

import com.budgetapp.app.budget.repositories.BudgetRepository
import com.budgetapp.app.budget.models.BudgetItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
public class BudgetService {


    private lateinit var budgetRepository: BudgetRepository

    @Autowired
    fun budgetService(budgetRepository: BudgetRepository) {
        this.budgetRepository = budgetRepository
    }

    fun getAll(): List<BudgetItem?>? {
        return budgetRepository.selectAllBudgets()
    }

    fun addNew(budgetItem: BudgetItem?) {
        if (budgetItem != null) {
            budgetRepository.insertBudget(budgetItem)
        }
    }


}
