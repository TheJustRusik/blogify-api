package dev.kenuki.blogifyapi.web.controller

import dev.kenuki.blogifyapi.core.service.CommentService
import dev.kenuki.blogifyapi.web.dto.request.CommentDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {
    @PatchMapping("/{commentId}")
    suspend fun commentComment(@PathVariable commentId: String, @RequestBody commentDTO: CommentDTO) =
        commentService.commentComment(commentDTO, commentId)

    @PatchMapping("/like/{commentId}")
    suspend fun likeComment(@PathVariable commentId: String) =
        commentService.likeOrUnlikeComment(commentId)

    @GetMapping("/{commentId}")
    fun getCommentComments(@PathVariable commentId: String, @RequestParam pageNum: Int, @RequestParam pageSize: Int) =
        commentService.getComments(commentId, "comment", pageNum, pageSize)
}