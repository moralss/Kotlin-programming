package com.jdbc.jdbcexample.info

import org.springframework.stereotype.Component

data class Student(
        val studentId : Int,
           val firstName: String,
           val lastName: String
)
{

   fun Student() {

}

    override fun toString(): String {
        return "Student{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}'
    }

    enum class Gender {
        MALE, FEMALE
    }

}
