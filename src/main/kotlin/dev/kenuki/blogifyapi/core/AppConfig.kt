package dev.kenuki.blogifyapi.core

import dev.kenuki.blogifyapi.core.repo.SessionRedisRepository
import dev.kenuki.blogifyapi.web.filter.AuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity

class AppConfig(
    private val sessionRedisRepository: SessionRedisRepository
) {

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers("/auth/**").permitAll()
                it.anyExchange().authenticated()
            }
            .addFilterAt(
                AuthenticationFilter(sessionRedisRepository),
                SecurityWebFiltersOrder.AUTHENTICATION
            )

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder(12)

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManager { authentication ->
            Mono.just(authentication)
        }
    }

}