package commons.spring.security

import org.springframework.security.core.AuthenticationException

public class InvalidTokenException(override val message: String): AuthenticationException(message)