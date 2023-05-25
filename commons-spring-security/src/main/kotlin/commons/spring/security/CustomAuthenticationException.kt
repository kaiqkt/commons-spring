package commons.spring.security

import org.springframework.security.core.AuthenticationException

public class CustomAuthenticationException(override val message: String): AuthenticationException(message)