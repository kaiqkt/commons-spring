package commons.spring.security

import org.springframework.security.core.AuthenticationException

public class AccessTokenExpiredException(override val message: String): AuthenticationException(message)