package dev.kenuki.blogifyapi.core.service

import dev.kenuki.blogifyapi.core.exception.ApplicationException
import dev.kenuki.blogifyapi.core.model.Blog
import dev.kenuki.blogifyapi.core.model.Like
import dev.kenuki.blogifyapi.core.model.User
import dev.kenuki.blogifyapi.core.repo.BlogRepo
import dev.kenuki.blogifyapi.core.repo.LikeRepo
import dev.kenuki.blogifyapi.web.dto.request.CreateBlogDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlogService(
    private val blogRepo: BlogRepo,
    private val likeRepo: LikeRepo,
) {
    fun findAllBlogByCriteria(
        pageNum: Int,
        pageSize: Int,
        criteria: Blog.SortCriteria = Blog.SortCriteria.DATE,
        direction: Sort.Direction = Sort.Direction.DESC
    ): Flow<Blog> {
        val pageable = PageRequest.of(pageNum, pageSize, Sort.by(direction, criteria.value))
        return blogRepo.findAllBy(pageable)
    }

    suspend fun createBlog(createBlogDTO: CreateBlogDTO): Blog {
        val blog = Blog(
            content = createBlogDTO.content,
            images = emptyList(),
            ownerId = getUser().id!!
        )

        return blogRepo.save(blog)
    }

    @Transactional
    suspend fun likeOrUnlikeBlog(blogId: String): String {
        val user = getUser()
        val blog = blogRepo.findById(blogId) ?: throw ApplicationException("Post by id $blogId, not found")
        if (likeRepo.existsLikeByUserIdAndObjectIdAndObjectType(user.id!!, blogId, "blog")) {
            val like = likeRepo.findLikeByUserIdAndObjectIdAndObjectType(user.id, blogId, "blog")
                ?: throw ApplicationException("Like by userId=${user.id} and blogId=$blogId not found")
            likeRepo.delete(like)
            blog.likeCount--
            blogRepo.save(blog)
            return "Removed like"
        } else {
            val like = Like(userId = user.id, objectId = blogId, objectType = "blog")
            likeRepo.save(like)
            blog.likeCount++
            blogRepo.save(blog)
            return "Added like"
        }
    }

    private suspend fun getUser() =
        ReactiveSecurityContextHolder.getContext().awaitFirstOrNull()!!.authentication as User
}