package dev.kenuki.blogifyapi.core.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompoundIndex(name = "unique_like", def = "{'userId': 1, 'objectId': 1, 'objectType': 1}", unique = true)
data class Like(
    @Id
    val id: String? = null,

    val userId: String,
    val objectId: String,
    val objectType: String,
)