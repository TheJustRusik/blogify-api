package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.model.Blog
import dev.kenuki.blogifyapi.core.model.Comment
import dev.kenuki.blogifyapi.core.service.BlogService
import dev.kenuki.blogifyapi.core.service.CommentService
import dev.kenuki.blogifyapi.web.dto.request.CommentDTO
import dev.kenuki.blogifyapi.web.dto.request.CreateBlogDTO
import org.springframework.data.domain.Sort.Direction
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/blog")
class BlogController(
    private val blogService: BlogService,
    private val commentService: CommentService
) {
    @PostMapping
    suspend fun createBlog(@RequestBody createBlogDTO: CreateBlogDTO) =
        blogService.createBlog(createBlogDTO)

    @GetMapping
    fun getAllBlogs(
        @RequestParam pageNum: Int,
        @RequestParam pageSize: Int,
        @RequestParam criteria: Blog.SortCriteria,
        @RequestParam direction: Direction,
    ) = blogService.findAllBlogByCriteria(pageNum, pageSize, criteria, direction)

    @PatchMapping("/like/{blogId}")
    suspend fun likeBlog(@PathVariable blogId: String) = blogService.likeOrUnlikeBlog(blogId)

    @PatchMapping("/comment/{blogId}")
    suspend fun commentBlog(@PathVariable blogId: String, @RequestBody commentDTO: CommentDTO): Comment {
        return commentService.commentBlog(commentDTO, blogId)
    }

    @GetMapping("/comment/{blogId}")
    fun getBlogComments(@PathVariable blogId: String, @RequestParam pageNum: Int, @RequestParam pageSize: Int) =
        commentService.getComments(blogId, "blog", pageNum, pageSize)
}