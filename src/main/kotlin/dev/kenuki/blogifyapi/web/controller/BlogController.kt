package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.model.Blog
import dev.kenuki.blogifyapi.core.service.BlogService
import dev.kenuki.blogifyapi.web.dto.request.CreateBlogDTO
import org.springframework.data.domain.Sort.Direction
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/blog")
class BlogController(
    private val blogService: BlogService,
) {
    @PostMapping
    suspend fun createBlog(@RequestBody createBlogDTO: CreateBlogDTO) =
        blogService.createBlog(createBlogDTO)

    @GetMapping
    suspend fun getAllBlogs(
        @RequestParam pageNum: Int,
        @RequestParam pageSize: Int,
        @RequestParam criteria: Blog.SortCriteria,
        @RequestParam direction: Direction,
    ) = blogService.findAllBlogByCriteria(pageNum, pageSize, criteria, direction)

    @PatchMapping("/like/{postId}")
    suspend fun likePost(@PathVariable postId: String) = blogService.likeOrUnlikeBlog(postId)

    @PatchMapping("/comment/{postId}")
    suspend fun commentPost(@PathVariable postId: String) {

    }
}