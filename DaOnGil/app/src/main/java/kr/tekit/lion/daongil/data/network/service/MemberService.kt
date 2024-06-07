package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.member.MyDefaultInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyInfoResponse
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Tag

interface MemberService {
    @GET("member")
    suspend fun getMyInfo(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): MyInfoResponse

    @PATCH("member")
    suspend fun updateMyInfo()

    @GET("mypage")
    suspend fun getMyDefaultInfo(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): MyDefaultInfoResponse
}