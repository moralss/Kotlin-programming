package com.budgetapp.app.budget

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("api/students")
class StudentController @Autowired constructor(budgetService: BudgetService) {
    private val budgetService: BudgetService

    @GetMapping
    fun getAllBudgets(): List<BudgetItem?>? {
        return budgetService.getAllStudents()
    }

    @PostMapping
    fun addNewStudent(@RequestBody budgetItem: @Valid BudgetItem?) {
        if (budgetItem != null) {
            budgetService.addNewStudent(budgetItem)
        }
    }


    init {
        this.budgetService = budgetService
    }
}
