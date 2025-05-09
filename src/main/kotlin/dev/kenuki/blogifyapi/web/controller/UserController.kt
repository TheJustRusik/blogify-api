package dev.kenuki.blogifyapi.web.controller
import dev.kenuki.blogifyapi.core.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/{userId}")
    suspend fun getUser(@PathVariable userId: String) = userService.getById(userId)

    @PostMapping("/all")
    fun getAllByIds(@RequestBody ids: List<String>) = userService.getAllByIds(ids)
}