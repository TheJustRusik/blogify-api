package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.model.User
import dev.kenuki.blogifyapi.core.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    suspend fun getUser(@RequestParam username: String): User {
        return userService.findByUsername(username)
    }

    @PostMapping
    suspend fun createUser(@RequestBody user: User): User {
        return userService.create(user)
    }
}