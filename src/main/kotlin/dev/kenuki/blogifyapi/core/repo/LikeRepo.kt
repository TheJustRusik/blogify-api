package dev.kenuki.blogifyapi.core.repo

import dev.kenuki.blogifyapi.core.model.Like
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface LikeRepo : CoroutineCrudRepository<Like, String> {
    suspend fun existsLikeByUserIdAndObjectIdAndObjectType(
        userId: String,
        objectId: String,
        objectType: String
    ): Boolean

    suspend fun findLikeByUserIdAndObjectIdAndObjectType(
        userId: String,
        objectId: String,
        objectType: String
    ): Like?
}