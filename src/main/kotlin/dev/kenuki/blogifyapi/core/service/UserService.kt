package dev.kenuki.blogifyapi.core.service

import dev.kenuki.blogifyapi.core.model.User
import dev.kenuki.blogifyapi.core.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepo,
) {
    suspend fun findByUsername(username: String): User = userRepo.findByUsername(username)

    suspend fun create(user: User): User = userRepo.save(user)

}