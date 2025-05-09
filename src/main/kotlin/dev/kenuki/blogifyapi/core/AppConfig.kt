package dev.kenuki.blogifyapi.core

import dev.kenuki.blogifyapi.core.repo.SessionRedisRepository
import dev.kenuki.blogifyapi.web.filter.AuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
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
                it
                    .pathMatchers("/auth/**", "/user/all").permitAll()
                    .pathMatchers(HttpMethod.GET, "/**").permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(
                AuthenticationFilter(sessionRedisRepository),
                SecurityWebFiltersOrder.AUTHENTICATION
            )

        return http.build()
    }

    @Bean
    fun corsFilter(): CorsWebFilter {
        val config = CorsConfiguration().apply {
            addAllowedOrigin("http://localhost:5173")
            addAllowedHeader("*")
            addAllowedMethod("*")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
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