package com.budgetapp.app.budget.repositories

import com.budgetapp.app.budget.models.MonthlyBudget
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.awt.print.Book
import java.sql.ResultSet
import java.util.*


@Repository
class MonthlyRepository {
    private var jdbcTemplate: JdbcTemplate? = null

    @Autowired
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate? = null
    @Autowired
    fun MontlyRepository (jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun selectAllMonthly(): MutableList<MonthlyBudget>? {
        var rowMapper: RowMapper<MonthlyBudget> = RowMapper<MonthlyBudget>
        { resultSet: ResultSet, rowIndex: Int ->
            MonthlyBudget(
                    resultSet.getInt("id"),
                    resultSet.getString("date_budget"),
                    resultSet.getDouble("month_budget"))
        }

        var results = jdbcTemplate?.query("SELECT * FROM monthly_budgets", rowMapper);
        return results;
    }


    fun insertMonthly(monthlyBudget: MonthlyBudget): Int {
        val sql = "" +
                "INSERT INTO monthly_budgets (" +
                " date_budget, " +
                " month_budget ) " +
                "VALUES (?, ?)"
        return jdbcTemplate!!.update(
                sql,
                monthlyBudget.budgetDate,
                monthlyBudget.budgetTotal
        )
    }


    fun getNameById(id: Long): MonthlyBudget? {
        return jdbcTemplate!!.queryForObject(
                "select * from monthly_budgets where id = ?", arrayOf<Any>(id),
                MonthlyBudget::class.java
        )
    }

    fun findByMonthDate(dateBudget: String): MonthlyBudget? {
        if (namedParameterJdbcTemplate != null) {
            return namedParameterJdbcTemplate.queryForObject(
            "SELECT * FROM monthly_budgets where date_budget = :dateBudget",
                    MapSqlParameterSource("dateBudget", dateBudget)
            ) { rs, rowNum ->
                (MonthlyBudget(
                        rs.getInt("id"),
                        rs.getString("date_budget"),
                        rs.getDouble("month_budget")
                ))
            }
        }
        return null
    }

}

