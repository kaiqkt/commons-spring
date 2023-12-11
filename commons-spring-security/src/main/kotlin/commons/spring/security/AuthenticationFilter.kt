package commons.spring.security


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
public class AuthenticationFilter(
    private val authenticationProperties: AuthenticationProperties,
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : OncePerRequestFilter() {

    private val handler = AuthenticationHandler()
    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader(HttpHeaders.AUTHORIZATION)?.let {
            try {
                val result: Authentication = when {
                    it.startsWith(BEARER_PREFIX) -> {
                        val customerAuthSecret = authenticationProperties.customerAuthSigningSecret
                            ?: throw IllegalArgumentException("Access token secret not provided")

                        handler.handleAccessToken(customerAuthSecret, it.replace(BEARER_PREFIX, ""))
                    }

                    else -> {
                        val serviceSharedSecret = authenticationProperties.serviceSharedSecret
                            ?: throw IllegalArgumentException("Service secret not provided")

                        handler.handleServiceToken(serviceSharedSecret, it)
                    }
                }

                SecurityContextHolder.getContext().authentication = result
                filterChain.doFilter(request, response)
            } catch (ex: AuthenticationException) {
                SecurityContextHolder.clearContext()
                restAuthenticationEntryPoint.commence(request, response, ex)
            }
        } ?: filterChain.doFilter(request, response)
    }

}