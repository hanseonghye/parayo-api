package me.han.parayo.auth

data class SignResponse(
        val token: String,
        val refreshToken: String,
        val userNAme: String,
        val userId: Long
)