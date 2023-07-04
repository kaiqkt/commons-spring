package commons.spring.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

public data class Authentication(
    val claims: Map<String, Any>,
    private val token: String,
    private var authenticated: Boolean = false
) : Authentication {

    public fun getClaim(key: String): String =
        claims[key]?.toString() ?: throw UnauthorizedException("Invalid Authorization token")

    override fun getName(): String = claims["name"]?.toString() ?: "PRINCIPAL"
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val role = claims["role"]?.toString() ?: "ROLE_USER"

        return mutableListOf(SimpleGrantedAuthority(role))
    }

    override fun getCredentials(): Any = token

    override fun getDetails(): Any = claims

    override fun getPrincipal(): Any = "PRINCIPAL"
    override fun isAuthenticated(): Boolean = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }
}