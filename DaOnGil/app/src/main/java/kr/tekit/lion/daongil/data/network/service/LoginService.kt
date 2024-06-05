package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.login.UserLoginResponse
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("v1/auth/sign-in") // API Url
    suspend fun getUserInfo(
        @Header("Authorization") token: String, // Query, Header 등에만 이름 붙힘
        @Query("type") type: String // 로그인 타입
    ): UserLoginResponse
}