package commons.spring.security

import org.springframework.security.core.AuthenticationException

public class UnauthorizedException(override val message: String): AuthenticationException(message)