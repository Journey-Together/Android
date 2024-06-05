package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.signin.SignInResponse
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface AuthService {
    @POST("sign-in")
    suspend fun signIn(
        @Query("type") type: String,
        @Header("Authorization") token: String,
        @Tag authType: AuthType = AuthType.NO_AUTH
    ): SignInResponse

    @POST("sign-in")
    suspend fun login(
        @Query("type") type: String,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): SignInResponse
}