package dev.kenuki.blogifyapi.core.model

import dev.kenuki.blogifyapi.web.dto.response.UserDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Document("users")
data class User(
    @Id
    val id: String? = null,
    @Indexed(unique = true)
    val username: String,
    @Indexed(unique = true)
    val email: String,
    val password: String,
    val status: String = "Nookie :3"
) : Authentication {
    override fun getName() = username

    override fun getAuthorities() = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))

    override fun getCredentials() = password

    override fun getDetails() = email

    override fun getPrincipal() = this

    override fun isAuthenticated() = true

    override fun setAuthenticated(isAuthenticated: Boolean) {
        println("RAHHHHH")
    }

    fun toDto() = UserDTO(
        id = id!!,
        username = username,
        status = status,
    )

}