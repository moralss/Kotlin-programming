package com.jdbc.jdbcexample.info

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet


data class Class(val classname : String);



@Repository
class StudentDataAccessService {
    private var jdbcTemplate: JdbcTemplate? = null

    @Autowired
    fun StudentDataAccessService(jdbcTemplate: JdbcTemplate?) {
        this.jdbcTemplate = jdbcTemplate
    }

    fun selectAllStudents(): MutableList<Student>? {

        var rowMapper: RowMapper<Student> = RowMapper<Student>
        { resultSet: ResultSet, rowIndex: Int ->
            Student(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"))
        }


        var results = jdbcTemplate?.query("SELECT * FROM buget", rowMapper);
        return results;
    }


    fun insertStudent(student: Student): Int {
        val sql = "" +
                "INSERT INTO buget (" +
                " first_name, " +
                " last_name ) " +
                "VALUES (?, ?)"

        return jdbcTemplate!!.update(
                sql,
                student.firstName,
                student.lastName
        )
    }

}
