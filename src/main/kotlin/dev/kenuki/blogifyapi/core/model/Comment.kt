package dev.kenuki.blogifyapi.core.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Comment (
    @Id
    var id: String? = null,
    val parentId: String,
    val parentType: String,
    val authorId: String,
    val content: String,
    var likeCount: Int = 0,
    var commentCount: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now()
)