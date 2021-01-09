package me.han.parayo.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JWTUtil {
    private const val ISSUER = "parayo"
    private const val SUBJECT = "Auth"
    private const val EXPIRE_TIME = 60L*60*2*1000
    private const val REFRESH_EXPIRE_TIME = 60L*60*24*30*1000

    private val SECRET = "your-secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(SECRET)

    private val refreshSecret = "your-refresh-secret"
    private val refreshAlgorithm: Algorithm = Algorithm.HMAC256(refreshSecret)

    fun createToken(email: String) = JWT.create()
            .withIssuer(ISSUER)
            .withSubject(SUBJECT)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + EXPIRE_TIME))
            .withClaim(JWTClaims.EMAIL, email)
            .sign(algorithm)

    fun createRefreshToken(email: String) = JWT.create()
            .withIssuer(ISSUER)
            .withSubject(SUBJECT)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
            .withClaim(JWTClaims.EMAIL, email)
            .sign(refreshAlgorithm)

    object JWTClaims {
        const val EMAIL = "email"
    }
}