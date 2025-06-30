package io.github.dmitrysulman.sqsclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqsClientApplication

fun main(args: Array<String>) {
    runApplication<SqsClientApplication>(*args)
}
