package me.han.parayo.auth

import me.han.parayo.common.ParayoException
import me.han.parayo.domain.user.User
import me.han.parayo.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class SigninService @Autowired constructor(
        private val userRepository: UserRepository
) {

    fun signin(signinRequest: SigninRequest): SigninResponse {
        val user = userRepository
                .findByEmail(signinRequest.email.toLowerCase())
                ?: throw ParayoException("로그인 정보를 확인해주세요.")
        
        if (isNotValidPassword(signinRequest.password, user.password)) {
            throw  ParayoException("아이디나 로그인을 확인해주세요.")
        }

        return responseWithTokens(user)
    }


    fun isNotValidPassword(
            plain: String,
            hashed: String
    ) = BCrypt.checkpw(plain, hashed).not()


    private fun responseWithTokens(user: User) = user.id?.let { userId ->
        SigninResponse(
                JWTUtil.createToken(user.email),
                JWTUtil.createRefreshToken(user.email),
                user.name,
                userId
        )
    }
            ?: throw IllegalStateException("user.id 없음")

}