package com.budgetapp.app.budget.repositories

import com.budgetapp.app.budget.models.BudgetItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class BudgetRepository {
    private var jdbcTemplate: JdbcTemplate? = null

    @Autowired
    fun BudgetRepository (jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun selectAllBudgets(): MutableList<BudgetItem>? {

        var rowMapper: RowMapper<BudgetItem> = RowMapper<BudgetItem>
        { resultSet: ResultSet, rowIndex: Int ->
            BudgetItem(
                    resultSet.getInt("id"),
                    resultSet.getString("budget_name"),
                    resultSet.getDouble("budget_amount"))
        }


        var results = jdbcTemplate?.query("SELECT * FROM budgets", rowMapper);
        return results;
    }


    fun insertBudget(budgetItem: BudgetItem): Int {
        val sql = "" +
                "INSERT INTO budgets (" +
                " budget_name, " +
                " budget_amount ) " +
                "VALUES (?, ?)"

        return jdbcTemplate!!.update(
                sql,
                budgetItem.budgetName,
                budgetItem.budgetPrice
        )
    }

}
