package com.budgetapp.app.budget.repositories

import com.budgetapp.app.budget.models.BudgetItem
import com.budgetapp.app.budget.models.MonthlyBudget
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class BudgetRepository {
    private var jdbcTemplate: JdbcTemplate? = null

    @Autowired
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate? = null

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
                    resultSet.getDouble("budget_amount"),
                    resultSet.getInt("monthly_budget_id")
                    )
        }

        var results = jdbcTemplate?.query("SELECT * FROM budgets", rowMapper);
        return results;
    }

    fun findById(budgetId: Long ) :  MutableList<BudgetItem>?  {
            val budgetList = namedParameterJdbcTemplate?.query(
                    "SELECT * FROM budgets where monthly_budget_id = :budgetId",
                    MapSqlParameterSource("budgetId", budgetId)
            ) { resultSet, rowNum ->
                BudgetItem(
                        resultSet.getInt("id"),
                        resultSet.getString("budget_name"),
                        resultSet.getDouble("budget_amount"),
                        resultSet.getInt("monthly_budget_id")
                )
        }

        return budgetList;
    }

    fun insertBudget(budgetItem: BudgetItem): Int {
        val sql = "" +
                "INSERT INTO budgets (" +
                " budget_name, " +
                " budget_amount, " +
                " monthly_budget_id) " +
                "VALUES (?, ?, ?)"

        return jdbcTemplate!!.update(
                sql,
                budgetItem.budgetName,
                budgetItem.budgetPrice,
                budgetItem.monthlyBudgetId
        )
    }
    fun deleteById(id: Long?): Int {
        return jdbcTemplate!!.update(
                "delete from budgets where id = ?",
                id)
    }
}
