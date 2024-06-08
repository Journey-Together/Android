package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.request.MyIceInfoRequest
import kr.tekit.lion.daongil.data.dto.remote.request.MyPersonalInfoRequest
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyDefaultInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.member.MyInfoResponse
import kr.tekit.lion.daongil.data.network.AuthType
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Tag

interface MemberService {
    @GET("member")
    suspend fun getMyInfo(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): MyInfoResponse

    @PATCH("member")
    suspend fun modifyMyPersonalInfo(
        @Body request: MyPersonalInfoRequest,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    )

    @Multipart
    @PATCH("member")
    suspend fun modifyMyProfileImage(
        @Part profileImage: MultipartBody.Part,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    )

    @PATCH("member")
    suspend fun modifyMyIceInfo(
        @Body request: MyIceInfoRequest,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    )

    @GET("mypage")
    suspend fun getMyDefaultInfo(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): MyDefaultInfoResponse
}