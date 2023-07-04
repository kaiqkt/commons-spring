package commons.spring.encrypt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.ZonedDateTime
import java.util.*

public data class Jwt(
    val claims: Map<String, Any>,
    val isExpired: Boolean
)

public fun getClaims(accessToken: String, secret: String): Jwt {
    return try {
        val claims = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(accessToken).body

        Jwt(claims, false)
    } catch (ex: ExpiredJwtException) {
        Jwt(ex.claims, true)
    }
}

public fun generateJwt(secret: String, claims: Map<String, String>, dateTime: ZonedDateTime): String {
    val expiration = Date.from(dateTime.toInstant())

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
        .compact()
}
