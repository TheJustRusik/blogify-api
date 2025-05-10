package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.service.SecurityService
import dev.kenuki.blogifyapi.web.dto.request.LoginDTO
import dev.kenuki.blogifyapi.web.dto.request.RegisterDTO
import dev.kenuki.blogifyapi.web.dto.response.Token
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class SecurityController(
    private val securityService: SecurityService
) {
    @PostMapping("/register")
    @Operation(summary = "Endpoint for register new user")
    suspend fun register(@RequestBody registerDTO: RegisterDTO) {
        securityService.register(registerDTO)
    }

    @PostMapping("/login")
    @Operation(summary = "Endpoint for authenticate existing user")
    suspend fun login(@RequestBody loginDTO: LoginDTO): Token {
        return securityService.login(loginDTO)
    }
}