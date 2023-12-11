package commons.spring.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        val message = authenticationException.message ?: "Error in spring security filter"

        val error = when (authenticationException) {
            is AccessTokenExpiredException -> "ACCESS_TOKEN_EXPIRED"
            is InvalidTokenException -> "INVALID_SERVICE_TOKEN"
            else -> "AUTHENTICATION_ERROR"
        }
        val body = """
            {
                "type": "$error",
                "message": "$message"
            }
        """.trimIndent()

        response.outputStream.println(body)
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}
