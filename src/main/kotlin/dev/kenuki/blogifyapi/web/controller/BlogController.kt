package dev.kenuki.blogifyapi.web.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/blog")
class BlogController {
    @PostMapping
    suspend fun createBlog(): String {
        return "New blog!"
    }
}