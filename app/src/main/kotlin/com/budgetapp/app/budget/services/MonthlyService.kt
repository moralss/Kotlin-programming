package com.budgetapp.app.budget.services

import com.budgetapp.app.budget.repositories.BudgetRepository
import com.budgetapp.app.budget.models.BudgetItem
import com.budgetapp.app.budget.models.MonthlyBudget
import com.budgetapp.app.budget.repositories.MonthlyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Long.parseLong


@Service
public class MonthlyService {


    private lateinit var monthlyRepository: MonthlyRepository

    @Autowired
    fun monthlyService(monthlyRepository: MonthlyRepository) {
        this.monthlyRepository = monthlyRepository
    }

    fun getAll(): List<MonthlyBudget?>? {
        return monthlyRepository.selectAllMonthly()
    }

    fun addNew(budgetItem: MonthlyBudget?){
        if (budgetItem != null) {
            var id = monthlyRepository.insertMonthly(budgetItem)
           print("this is my id" + id)
//            return monthlyRepository.findById(parseLong(parseLong(id)) );
        }
    }

    fun getOne( dateBudget: String): MonthlyBudget? {
       return monthlyRepository.findByMonthDate(dateBudget );
    }


}
