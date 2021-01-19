package me.han.parayo.auth

import me.han.parayo.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired

class UserContextHolder @Autowired constructor(
        private val userRepository: UserRepository
) {
    private val userHolder = ThreadLocal.withInitial { UserHolder() }


    val id: Long? get() = userHolder.get().id
    val name: String? get() = userHolder.get().name
    val email: String? get() = userHolder.get().email


    fun set(email: String) = userRepository
            .findByEmail(email)?.let {
                user ->
                 this.userHolder.get().apply {
                     this.id = user.id
                     this.email = user.email
                     this.name = user.name
                 }.run(userHolder::set)
            }


    fun clear() {
        userHolder.remove()
    }


    class UserHolder {
        var id: Long? = null
        var email: String? = null
        var name: String? = null
    }
}