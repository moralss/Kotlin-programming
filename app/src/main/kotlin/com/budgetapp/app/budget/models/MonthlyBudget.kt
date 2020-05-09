package com.budgetapp.app.budget.models

import java.util.*

data class MonthlyBudget(
        val budgetId: Int,
        val budgetDate: String,
        val budgetTotal: Double,
        val firebase_user_id: String
) {
    override fun toString(): String {
        return "Student{" +
                ", firstName='" + budgetDate + '\'' +
                ", lastName='" + budgetTotal + '\'' +
                '}'
    }
}
