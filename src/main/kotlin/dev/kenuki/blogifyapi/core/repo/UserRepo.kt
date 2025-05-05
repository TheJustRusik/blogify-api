package dev.kenuki.blogifyapi.core.repo

import dev.kenuki.blogifyapi.core.model.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepo : CoroutineCrudRepository<User, String> {
    suspend fun findByEmail(email: String): User?
    suspend fun findByUsername(username: String): User?
    suspend fun existsUsersByUsernameOrEmail(username: String, email: String): Boolean

}