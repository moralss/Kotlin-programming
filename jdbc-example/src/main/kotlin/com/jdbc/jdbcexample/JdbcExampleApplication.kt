package com.jdbc.jdbcexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@SpringBootApplication
@ComponentScan("com.jdbc.jdbcexample.info")

class JdbcExampleApplication

fun main(args: Array<String>) {
	runApplication<JdbcExampleApplication>(*args)
}
