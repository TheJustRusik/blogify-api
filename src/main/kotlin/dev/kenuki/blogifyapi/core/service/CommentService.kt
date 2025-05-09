package dev.kenuki.blogifyapi.core.service

import dev.kenuki.blogifyapi.core.exception.ApplicationException
import dev.kenuki.blogifyapi.core.model.Comment
import dev.kenuki.blogifyapi.core.model.Like
import dev.kenuki.blogifyapi.core.model.User
import dev.kenuki.blogifyapi.core.repo.CommentRepo
import dev.kenuki.blogifyapi.core.repo.LikeRepo
import dev.kenuki.blogifyapi.web.dto.request.CommentDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepo: CommentRepo,
    private val likeRepo: LikeRepo,
) {
    suspend fun commentObject(objectId: String, objectType: String, commentDTO: CommentDTO): Comment {
        val comment = Comment(
            parentId = objectId,
            parentType = objectType,
            authorId = getUser().id!!,
            content = commentDTO.content,
        )
        return commentRepo.save(comment)
    }

    suspend fun commentBlog(commentDTO: CommentDTO, blogId: String) =
        commentObject(blogId, "blog", commentDTO)

    suspend fun commentComment(commentDTO: CommentDTO, commentId: String) =
        commentObject(commentId, "comment", commentDTO)

    @Transactional
    suspend fun likeOrUnlikeComment(commentId: String): String {
        val user = getUser()
        val comment = commentRepo.findById(commentId) ?: throw ApplicationException("Comment by id $commentId, not found")
        if (likeRepo.existsLikeByUserIdAndObjectIdAndObjectType(user.id!!, commentId, "comment")) {
            val like = likeRepo.findLikeByUserIdAndObjectIdAndObjectType(user.id, commentId, "comment")
                ?: throw ApplicationException("Like by userId=${user.id} and blogId=$commentId not found")
            likeRepo.delete(like)
            comment.likeCount--
            commentRepo.save(comment)
            return "Removed like"
        } else {
            val like = Like(userId = user.id, objectId = commentId, objectType = "comment")
            likeRepo.save(like)
            comment.likeCount++
            commentRepo.save(comment)
            return "Added like"
        }
    }

    fun getComments(objectId: String, objectType: String, pageNum: Int, pageSize: Int): Flow<Comment> {
        val pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "likeCount"))
        return commentRepo.findAllByParentIdAndParentType(objectId, objectType, pageable)
    }

    private suspend fun getUser() =
        ReactiveSecurityContextHolder.getContext().awaitFirstOrNull()!!.authentication as User
}