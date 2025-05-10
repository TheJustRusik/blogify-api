package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.model.Blog
import dev.kenuki.blogifyapi.core.model.Comment
import dev.kenuki.blogifyapi.core.service.BlogService
import dev.kenuki.blogifyapi.core.service.CommentService
import dev.kenuki.blogifyapi.web.dto.request.CommentDTO
import dev.kenuki.blogifyapi.web.dto.request.CreateBlogDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Sort.Direction
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/blog")
class BlogController(
    private val blogService: BlogService,
    private val commentService: CommentService
) {
    @PostMapping
    @Operation(summary = "Endpoint for creating blog, use only when authenticated")
    suspend fun createBlog(@RequestBody createBlogDTO: CreateBlogDTO) =
        blogService.createBlog(createBlogDTO)

    @GetMapping
    @Operation(summary = "Endpoint for get all blogs, use pageNum=0 for first page")
    fun getAllBlogs(
        @RequestParam pageNum: Int,
        @RequestParam pageSize: Int,
        @RequestParam criteria: Blog.SortCriteria,
        @RequestParam direction: Direction,
    ) = blogService.findAllBlogByCriteria(pageNum, pageSize, criteria, direction)

    @PatchMapping("/like/{blogId}")
    @Operation(summary = "Endpoint for like blog, use only when authenticated")
    suspend fun likeBlog(@PathVariable blogId: String) = blogService.likeOrUnlikeBlog(blogId)

    @PatchMapping("/comment/{blogId}")
    @Operation(summary = "Endpoint for commenting blog, use only when authenticated")
    suspend fun commentBlog(@PathVariable blogId: String, @RequestBody commentDTO: CommentDTO): Comment {
        return commentService.commentBlog(commentDTO, blogId)
    }

    @GetMapping("/comment/{blogId}")
    @Operation(summary = "Endpoint for get all blog's comments, use pageNum=0 for first page")
    fun getBlogComments(@PathVariable blogId: String, @RequestParam pageNum: Int, @RequestParam pageSize: Int) =
        commentService.getComments(blogId, "blog", pageNum, pageSize)
}