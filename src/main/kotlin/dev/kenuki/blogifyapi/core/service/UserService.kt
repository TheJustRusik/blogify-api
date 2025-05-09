package dev.kenuki.blogifyapi.core.service

import dev.kenuki.blogifyapi.core.model.User
import dev.kenuki.blogifyapi.core.repo.UserRepo
import dev.kenuki.blogifyapi.web.dto.response.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepo
) {
    suspend fun getById(id: String): User? {
        return userRepo.findById(id)
    }

    fun getAllByIds(userIds: List<String>): Flow<UserDTO> {
        return userRepo.findAllByIdIn(userIds).map { it.toDto() }
    }
}