package commons.spring.security

import org.springframework.security.core.context.SecurityContextHolder

public fun getAuthentication(): Authentication {
    val context = SecurityContextHolder.getContext()
    return context.authentication as Authentication
}