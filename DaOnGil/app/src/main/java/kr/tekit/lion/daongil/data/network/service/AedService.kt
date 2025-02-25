package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.BuildConfig
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.aed.AedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AedService {
    @GET("getEgytAedManageInfoInqire")
    suspend fun getAedInfo(
        @Query("Q0") q0: String?,
        @Query("Q1") q1: String?,
        @Query("numOfRows") numOfRows: Int = 48568,
        @Query("_type") type: String = "json",
        @Query("serviceKey") serviceKey: String = BuildConfig.EMERGENCY_API_KEY
    ) : AedResponse
}