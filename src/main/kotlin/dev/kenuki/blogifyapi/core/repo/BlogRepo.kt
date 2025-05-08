package dev.kenuki.blogifyapi.core.repo

import dev.kenuki.blogifyapi.core.model.Blog
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BlogRepo : CoroutineCrudRepository<Blog, String> {
    fun findAllBy(pageable: Pageable): Flow<Blog>
}