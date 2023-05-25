package commons.spring.security

import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

public class AuthenticationFilter(
    private val authenticationProperties: AuthenticationProperties,
    authenticationManager: AuthenticationManager,
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val accessToken = request.getHeader(AUTHORIZATION)?.let {
            try {
                val result = when{
                    it.startsWith(BEARER_PREFIX) -> {
                        val customerAuthSecret = authenticationProperties.customerAuthSigningSecret ?: throw CustomAuthenticationException("Access token secret not provided")
                    }
                    else -> {
                        val serviceSharedSecret = authenticationProperties.serviceSharedSecret ?: throw CustomAuthenticationException("Service secret not provided")

                    }
                }
            }catch (e: AuthenticationException) {
                SecurityContextHolder.clearContext()
                onUnsuccessfulAuthentication(request, response, e)
                restAuthenticationEntryPoint.commence(request, response, e)
            }
        } ?: chain.doFilter(request, response)
    }
}