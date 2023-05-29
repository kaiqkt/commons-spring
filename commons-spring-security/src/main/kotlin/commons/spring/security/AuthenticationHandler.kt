package commons.spring.security

import commons.spring.encrypt.parseJwtClaims

public class AuthenticationHandler {

    public fun handleAccessToken(accessTokenSecret: String,accessToken: String): Authentication {
        val claims = parseJwtClaims(accessToken, accessTokenSecret)

        return Authentication(
            token = accessToken,
            claims = claims,
            role = ROLE_USER,
            authenticated = true
        )
    }

    public fun handleServiceToken(serviceSecret: String, serviceToken: String): Authentication {

        if (serviceToken == serviceSecret) {
            Authentication(
                token = serviceToken,
                claims = mapOf(),
                role = ROLE_SERVICE,
                authenticated = true
            )
        }

        throw CustomAuthenticationException("Invalid service token")
    }
}