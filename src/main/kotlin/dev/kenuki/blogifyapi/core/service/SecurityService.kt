package dev.kenuki.blogifyapi.core.service

import dev.kenuki.blogifyapi.core.model.User
import dev.kenuki.blogifyapi.core.repo.SessionRedisRepository
import dev.kenuki.blogifyapi.core.repo.UserRepo
import dev.kenuki.blogifyapi.web.dto.request.LoginDTO
import dev.kenuki.blogifyapi.web.dto.request.RegisterDTO
import dev.kenuki.blogifyapi.web.dto.response.Token
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SecurityService(
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder,
    private val sessionRedisRepository: SessionRedisRepository,
) {
    private final val log = LoggerFactory.getLogger(SecurityService::class.java)

    suspend fun register(registerDTO: RegisterDTO) {
        if (userRepo.existsUsersByUsernameOrEmail(registerDTO.username, registerDTO.email))
            throw Exception("User ${registerDTO.username} already exists")

        val user = with(registerDTO) {
            User(
                username = username,
                email = email,
                password = passwordEncoder.encode(password),
            )
        }
        userRepo.save(user)
    }

    suspend fun login(loginDTO: LoginDTO): Token {
        val user = with(loginDTO) {
            userRepo.findByEmail(email) ?: throw Exception("User $email not found")
        }

        if (!passwordEncoder.matches(loginDTO.password, user.password))
            throw Exception("Wrong password!")

        val accessToken = sessionRedisRepository.saveUserSession(user)
        return Token(accessToken)
    }
}