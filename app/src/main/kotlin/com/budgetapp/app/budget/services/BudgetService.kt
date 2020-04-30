package com.budgetapp.app.budget.services

import com.budgetapp.app.budget.models.BudgetItem
import com.budgetapp.app.budget.repositories.BudgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Long.parseLong


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

    fun getAllBudgetsById(id : String) : MutableList<BudgetItem>? {
        return budgetRepository.findById(parseLong(id))
    }

    fun addNew(budgetItem: BudgetItem?) {
        if (budgetItem != null) {
            budgetRepository.insertBudget(budgetItem)
        }
    }

    fun deleteById( id: String) {
        budgetRepository.deleteById(parseLong(id))
    }


}
