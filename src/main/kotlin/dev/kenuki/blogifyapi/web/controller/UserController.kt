package dev.kenuki.blogifyapi.web.controller
import dev.kenuki.blogifyapi.core.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/{userId}")
    @Operation(summary = "Endpoint for get user by id")
    suspend fun getUser(@PathVariable userId: String) = userService.getById(userId)

    @PostMapping("/all")
    @Operation(summary = "Endpoint for get many users by ids")
    fun getAllByIds(@RequestBody ids: List<String>) = userService.getAllByIds(ids)
}