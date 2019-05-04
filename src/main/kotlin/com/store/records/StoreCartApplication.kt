package com.store.records

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class UniversityRecordApplication

fun main(args: Array<String>) {
    runApplication<UniversityRecordApplication>(*args)
}