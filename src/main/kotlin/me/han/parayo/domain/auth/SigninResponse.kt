package me.han.parayo.domain.auth

data class SigninResponse(
        val token: String,
        val refreshToken: String,
        val userNAme: String,
        val userId: Long
)