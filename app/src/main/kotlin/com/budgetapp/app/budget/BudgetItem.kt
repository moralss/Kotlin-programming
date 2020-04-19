package com.budgetapp.app.budget

import org.springframework.stereotype.Component
import java.text.DecimalFormat

data class BudgetItem(
        val budgetId : Int,
        val budgetName: String,
        val budgetPrice: Double
)
{
    fun Student() {

    }

    override fun toString(): String {
        return "Student{" +
                ", firstName='" + budgetName + '\'' +
                ", lastName='" + budgetPrice + '\'' +
                '}'
    }

    enum class Gender {
        MALE, FEMALE
    }

}
