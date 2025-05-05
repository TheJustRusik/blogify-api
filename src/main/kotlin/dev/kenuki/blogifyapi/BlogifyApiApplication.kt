package dev.kenuki.blogifyapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogifyApiApplication

fun main(args: Array<String>) {
    runApplication<BlogifyApiApplication>(*args)
}
