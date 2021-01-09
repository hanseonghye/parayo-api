package me.han.parayo.auth

import me.han.parayo.common.ParayoException
import me.han.parayo.domain.user.User
import me.han.parayo.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import java.lang.IllegalStateException

class SignService @Autowired constructor(
        private val userRepository: UserRepository
) {

    fun signin(signinRequest: SignRequest): SignResponse {
        val user = userRepository
                .findByEmail(signinRequest.email.toLowerCase())
                ?: throw ParayoException("로그인 정보를 확인해주세요.")

        return responseWithTokens(user)
    }

    private fun responseWithTokens(user: User) = user.id?.let { userId ->
        SignResponse(
                JWTUtil.createToken(user.email),
                JWTUtil.createRefreshToken(user.email),
                user.name,
                userId
        )
    }
            ?: throw IllegalStateException("user.id 없음")

}