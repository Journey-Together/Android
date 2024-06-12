package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.Interest.InterestTypeRes
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Tag

interface InterestService {
    // 관심 유형 정보 저장
    @PATCH("member/interest-type")
    suspend fun updateInterestType(
        @Body interestTypeRes: InterestTypeRes,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    )

    // 관심 유형 정보 불러오기
    @GET("member/interest-type")
    suspend fun getInterestType(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): InterestTypeRes
}