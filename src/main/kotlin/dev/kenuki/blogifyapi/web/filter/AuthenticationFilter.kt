package dev.kenuki.blogifyapi.web.filter

import dev.kenuki.blogifyapi.core.repo.SessionRedisRepository
import kotlinx.coroutines.reactor.asCoroutineContext
import kotlinx.coroutines.withContext
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange

class AuthenticationFilter(
    private val sessionRedisRepository: SessionRedisRepository
) : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        var token = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: return chain.filter(exchange)

        token = token.substringAfter("Bearer ")

        val user = sessionRedisRepository
            .getUserSession(token)
            ?: return chain.filter(exchange)

        val context = ReactiveSecurityContextHolder.withAuthentication(user)

        withContext(context.asCoroutineContext()) {
            chain.filter(exchange)
        }
    }
}