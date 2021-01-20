package me.han.parayo.domain.auth

data class SigninRequest(
        val email: String,
        val password: String
)