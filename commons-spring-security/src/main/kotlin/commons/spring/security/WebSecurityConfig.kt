package commons.spring.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(AuthenticationProperties::class)
public class WebSecurityConfig(
    private val authenticationProperties: AuthenticationProperties,
    private val authenticationFilter: AuthenticationFilter
) {
    @Bean
    @Throws(Exception::class)
    public fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
        http.cors { cors -> cors.disable() }
        http.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.GET, *authenticationProperties.ignoreGetPaths).permitAll()
            it.requestMatchers(HttpMethod.POST, *authenticationProperties.ignorePostPaths).permitAll()
            it.requestMatchers(HttpMethod.PATCH, *authenticationProperties.ignorePatchPaths).permitAll()
            it.requestMatchers(HttpMethod.DELETE, *authenticationProperties.ignoreDeletePaths).permitAll()
            it.requestMatchers(*PATH_MATCHERS.plus(authenticationProperties.ignoreGenericPaths)).permitAll()
            it.anyRequest().authenticated()
        }
        http.addFilterBefore(authenticationFilter, AuthorizationFilter::class.java)

        return http.build()
    }

    public companion object {
        private val PATH_MATCHERS = arrayOf(
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/api-docs.yml",
            "/docs",
            "/health-check"
        )
    }
}