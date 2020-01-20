package com.jdbc.jdbcexample.info

import com.jdbc.jdbcexample.info.Student
import com.jdbc.jdbcexample.info.StudentDataAccessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
public class StudentService {


    private lateinit var studentDataAccessService: StudentDataAccessService

    @Autowired
    fun StudentService(studentDataAccessService: StudentDataAccessService) {
        this.studentDataAccessService = studentDataAccessService
    }

    fun getAllStudents(): List<Student?>? {
        return studentDataAccessService.selectAllStudents()
    }

    fun addNewStudent(student: Student?) {
        if (student != null) {
            studentDataAccessService.insertStudent(student)
        }
    }


}
