package dev.kenuki.blogifyapi.core.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Blog (
    @Id
    val id: String? = null,
    val content: String,
    val images: List<String>,
    var likeCount: Int = 0,
    var commentCount: Int = 0,
    val ownerId: String,
    val postedDate: LocalDateTime = LocalDateTime.now()
) {
    enum class SortCriteria(val value: String) {
        DATE("postedDate"),
        LIKE("likeCount"),
        COMMENT("commentCount"),
    }

}