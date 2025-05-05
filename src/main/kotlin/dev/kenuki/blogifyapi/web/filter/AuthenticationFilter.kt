package dev.kenuki.blogifyapi.web.filter

import dev.kenuki.blogifyapi.core.repo.SessionRedisRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.*

@Component
class AuthenticationFilter(
    private val sessionRedisRepository: SessionRedisRepository
): CoWebFilter() {
    private final val log = LoggerFactory.getLogger(AuthenticationFilter::class.java)
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        var token = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: return chain.filter(exchange)
        token = token.substringAfter("Bearer ")
        val user = sessionRedisRepository.getUserSession(token)
        log.info(user.toString())
        return chain.filter(exchange)
    }
}