package me.han.parayo.controller

import me.han.parayo.common.ApiResponse
import me.han.parayo.domain.auth.JWTUtil
import me.han.parayo.domain.auth.SigninRequest
import me.han.parayo.domain.auth.SigninService
import me.han.parayo.domain.auth.UserContextHolder
import me.han.parayo.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/v1")
class SigninController @Autowired constructor(
        private val signinService: SigninService,
        private val userContextHolder: UserContextHolder
){

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest) =
            ApiResponse.ok(signinService.signin(signinRequest))


    @PostMapping("/refresh_token")
    fun refreshToken(
            @RequestParam("grant_type") grantType: String
    ) : ApiResponse {
        if (grantType != TokenValidationInterceptor.GRANT_TYPE_REFRESH) {
            throw IllegalArgumentException("grant type 없음")
        }

        return userContextHolder.email?.let {
            ApiResponse.ok(JWTUtil.createToken(it))
        } ?: throw IllegalArgumentException("사용자 정보 없음")
    }
}