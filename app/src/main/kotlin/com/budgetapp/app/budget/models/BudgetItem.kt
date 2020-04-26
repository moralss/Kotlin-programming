package com.budgetapp.app.budget.models

data class BudgetItem(
        val budgetId : Int,
        val budgetName: String,
        val budgetPrice: Double
)
{

    override fun toString(): String {
        return "Student{" +
                ", firstName='" + budgetName + '\'' +
                ", lastName='" + budgetPrice + '\'' +
                '}'
    }
}
