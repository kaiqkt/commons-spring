package commons.spring.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

public class Authentication(
    private val token: String,
    private val role: String,
    private val claims: Map<String, Any>,
    private var authenticated: Boolean = false
) : Authentication {

    override fun getName(): String = claims["name"]?.toString() ?: "PRINCIPAL"
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role))

    override fun getCredentials(): Any = token

    override fun getDetails(): Any = claims

    override fun getPrincipal(): Any = "PRINCIPAL"
    override fun isAuthenticated(): Boolean = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }
}