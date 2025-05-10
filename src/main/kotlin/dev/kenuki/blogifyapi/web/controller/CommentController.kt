package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.service.CommentService
import dev.kenuki.blogifyapi.web.dto.request.CommentDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {
    @PatchMapping("/{commentId}")
    @Operation(summary = "Endpoint for commenting comment, use only when authenticated")
    suspend fun commentComment(@PathVariable commentId: String, @RequestBody commentDTO: CommentDTO) =
        commentService.commentComment(commentDTO, commentId)

    @PatchMapping("/like/{commentId}")
    @Operation(summary = "Endpoint for liking comment, use only when authenticated")
    suspend fun likeComment(@PathVariable commentId: String) =
        commentService.likeOrUnlikeComment(commentId)

    @GetMapping("/{commentId}")
    @Operation(summary = "Endpoint for get comment's comments, use only when authenticated")
    fun getCommentComments(@PathVariable commentId: String, @RequestParam pageNum: Int, @RequestParam pageSize: Int) =
        commentService.getComments(commentId, "comment", pageNum, pageSize)
}