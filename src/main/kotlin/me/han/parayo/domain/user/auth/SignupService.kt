package me.han.parayo.domain.user.auth

import me.han.parayo.common.ParayoException
import me.han.parayo.domain.user.User
import me.han.parayo.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignupService @Autowired constructor(
    private val userRepository: UserRepository
){

    fun signup(signupRequest: SignupRequest) {
        validateRequest(signupRequest)
        checkDuplicates(signupRequest.email)
        registerUser(signupRequest)
    }


    private fun validateRequest(signupRequest: SignupRequest) {
        validateEmail(signupRequest.email)
        validateName(signupRequest.name)
        validatePassword(signupRequest.password)
    }


    private fun validateEmail(email: String) {
        val isNotValidEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
                .toRegex(RegexOption.IGNORE_CASE)
                .matches(email)
                .not()

        if (isNotValidEmail) {
            throw ParayoException("이메일 형식이 올바르지 않습니다.")
        }
    }


    private fun validateName(name: String) {
        if (name.trim().length !in 2..20) {
            throw ParayoException("이름은 2자 이상 20자 이하여야 합니다.")
        }
    }


    private fun validatePassword(password: String) {
        if (password.trim().length !in 8..20) {
            throw ParayoException("비밀번호는 8자 이상 20자 이하여야 합니다.")
        }

    }


    private fun checkDuplicates(email: String) {
        userRepository.findByEmail(email)?.let{
            throw ParayoException("이미 사용중인 이메일 입니다.")
        }

    }


    private fun registerUser(signupRequest: SignupRequest) =
        with(signupRequest) {
            val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
            val user = User(email, hashedPassword, name)
            userRepository.save(user)
        }
}
