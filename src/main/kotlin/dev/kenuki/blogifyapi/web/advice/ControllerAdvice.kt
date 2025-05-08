package dev.kenuki.blogifyapi.web.advice

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(Exception::class)
    suspend fun handleBaseException(ex: Exception): String {
        return ex.message ?: ""
    }
}