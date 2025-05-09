package dev.kenuki.blogifyapi.core.repo

import dev.kenuki.blogifyapi.core.model.Comment
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CommentRepo : CoroutineCrudRepository<Comment, String> {
    fun findAllByParentIdAndParentType(parentId: String, parentType: String, pageable: Pageable): Flow<Comment>
}