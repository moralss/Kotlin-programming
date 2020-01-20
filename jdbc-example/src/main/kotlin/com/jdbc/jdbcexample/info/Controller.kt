package com.jdbc.jdbcexample.info

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("api/students")
class StudentController @Autowired constructor(studentService: StudentService) {
    private val studentService: StudentService

    @GetMapping
    fun getAllStudents(): List<Student?>? {
        return studentService.getAllStudents()
    }

    @PostMapping
    fun addNewStudent(@RequestBody student: @Valid Student?) {
        if (student != null) {
            studentService.addNewStudent(student)
        }
    }


    init {
        this.studentService = studentService
    }
}
