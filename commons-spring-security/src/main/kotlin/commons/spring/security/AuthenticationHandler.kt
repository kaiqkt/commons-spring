package commons.spring.security

import com.kaiqkt.commons.spring.encrypt.parseJwtClaims

public class AuthenticationHandler(
    private val serviceSecret: String,
    private val accessTokenSecret: String
) {

    public fun handleAccessToken(accessToken: String): Authentication {
        val claims = parseJwtClaims(accessToken, accessTokenSecret)

        return Authentication(
            token = accessToken,
            claims = claims,
            authenticated = true
        )
    }

    public fun handleServiceToken(serviceToken: String): Authentication {

        if (serviceToken == serviceSecret) {
            Authentication(
                token = serviceToken,
                claims = mapOf(),
                authenticated = true
            )
        }

        throw CustomAuthenticationException("Invalid service token")
    }
}