package commons.spring.security

import commons.spring.encrypt.getClaims

public class AuthenticationHandler {

    public fun handleAccessToken(accessTokenSecret: String, accessToken: String): Authentication {
        val jwt = getClaims(accessToken, accessTokenSecret)

        if (!jwt.isExpired) {
            return Authentication(
                token = accessToken,
                claims = jwt.claims,
                authenticated = true
            )
        }

        throw AccessTokenExpiredException("Access token expired")
    }

    public fun handleServiceToken(serviceSecret: String, serviceToken: String): Authentication {

        if (serviceToken == serviceSecret) {
            return Authentication(
                token = serviceToken,
                claims = mapOf("role" to ROLE_SERVICE),
                authenticated = true
            )
        }

        throw InvalidTokenException("Invalid service token")
    }
}