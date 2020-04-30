package com.budgetapp.app.budget.controllers

import com.budgetapp.app.budget.models.MonthlyBudget
import com.budgetapp.app.budget.services.MonthlyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("api/monthly")
class MonthlyController @Autowired constructor(monthlyService: MonthlyService) {
    private val monthlyService: MonthlyService

    @GetMapping
    fun getAllMonthlyBudgets(): List<MonthlyBudget?>? {
        return monthlyService.getAll()
    }

    @GetMapping("/api/monthly/single")
    fun findSingleMonthlyBudgets(@RequestParam id: String): MonthlyBudget? {
        return monthlyService.getOne("2014-11-01")

    }


    @PostMapping
    fun addNewMonthlyBudget(@RequestBody monthlyBudget: @Valid MonthlyBudget?): MonthlyBudget? {
        if (monthlyBudget != null) {
            monthlyService.addNew(monthlyBudget)
            return monthlyService.getOne(monthlyBudget.budgetDate)
        }

        return null
    }

    init {
        this.monthlyService = monthlyService
    }
}