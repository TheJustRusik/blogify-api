package dev.kenuki.blogifyapi.web.dto.request

data class RegisterDTO(
    val username: String,
    val email: String,
    val password: String
)
