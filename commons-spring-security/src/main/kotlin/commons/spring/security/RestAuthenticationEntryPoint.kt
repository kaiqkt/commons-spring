package commons.spring.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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

        val error = when(authenticationException) {
            is AccessTokenExpiredException -> "ACCESS_TOKEN_EXPIRED"
            is InvalidTokenException -> "INVALID_SERVICE_TOKEN"
            else -> "AUTHENTICATION_ERROR"
        }
        val body = mapOf("type" to error, "message" to message)

        response.outputStream.println(jacksonObjectMapper().writeValueAsString(body))
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}
