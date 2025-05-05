package dev.kenuki.blogifyapi.core.repo

import dev.kenuki.blogifyapi.core.model.User
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration
import java.util.*

@Repository
class SessionRedisRepository(
    private val redisTemplate: ReactiveRedisTemplate<String, Any>,
    private val userRepo: UserRepo,
) {
    companion object {
        val TOKEN_TTL = Duration.ofDays(2)!!;
    }

    suspend fun saveUserSession(user: User): String {
        val token = "${UUID.randomUUID()}${UUID.randomUUID()}".replace("-", "")
        redisTemplate.opsForValue().set(token, user.id!!, TOKEN_TTL).awaitFirstOrNull()
        return token
    }

    suspend fun getUserSession(token: String): User {
        val userId = redisTemplate.opsForValue()
            .get(token).awaitFirstOrNull() as? String
            ?: throw Exception("Token not found")

        //reset TTL
        redisTemplate.expire(token, TOKEN_TTL).awaitFirstOrNull()

        return userRepo.findById(userId)!!
    }

}