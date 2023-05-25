package com.kaiqkt.commons.spring.encrypt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

public fun generateJwt(secret: String, claims: Map<String, String>, expiration: Date): String {

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
        .compact()
}

public fun parseJwtClaims(jwt: String, secret: String): Map<String, Any> {
    return Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.toMap()
}