package com.budgetapp.app.budget.controllers

import com.budgetapp.app.budget.models.BudgetItem
import com.budgetapp.app.budget.models.MonthlyBudget
import com.budgetapp.app.budget.services.BudgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("api/budget")
class BudgetController @Autowired constructor(budgetService: BudgetService) {
    private val budgetService: BudgetService

    @GetMapping
    fun getAllBudgets(@RequestParam id: String): MutableList<BudgetItem>? {
        return budgetService.getAllBudgetsById(id)
    }

    @DeleteMapping
    fun deleteSingleMonthyBudget(@RequestParam id: String) {
        return budgetService.deleteById(id)
    }

    @PostMapping
    fun addNewBudget(@RequestBody budgetItem: @Valid BudgetItem?) {
        if (budgetItem != null) {
            budgetService.addNew(budgetItem)
        }
    }

    init {
        this.budgetService = budgetService
    }
}
