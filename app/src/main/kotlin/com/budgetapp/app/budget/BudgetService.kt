package com.budgetapp.app.budget

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
public class BudgetService {


    private lateinit var budgetDataAccessService: BudgetDataAccessService

    @Autowired
    fun StudentService(studentDataAccessService: BudgetDataAccessService) {
        this.budgetDataAccessService = studentDataAccessService
    }

    fun getAllStudents(): List<BudgetItem?>? {
        return budgetDataAccessService.selectAllBudgets()
    }

    fun addNewStudent(budgetItem: BudgetItem?) {
        if (budgetItem != null) {
            budgetDataAccessService.insertBudgets(budgetItem)
        }
    }


}
