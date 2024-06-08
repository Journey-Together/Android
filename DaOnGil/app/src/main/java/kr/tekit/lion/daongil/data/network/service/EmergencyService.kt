package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.BuildConfig
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.basic.EmergencyBasicResponse
import kr.tekit.lion.daongil.data.dto.remote.response.emergency.realtime.EmergencyRealtimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EmergencyService {

    // 응급실 지역 실시간 정보
    @GET("getEmrrmRltmUsefulSckbdInfoInqire")
    suspend fun getEmergencyRealtime(
        @Query("STAGE1") STAGE1: String?,
        @Query("STAGE2") STAGE2: String?,
        @Query("numOfRows") numOfRows: Int = 1000,
        @Query("_type") _type: String = "json",
        @Query("serviceKey") serviceKey: String = BuildConfig.EMERGENCY_API_KEY
    ) : EmergencyRealtimeResponse

    // 응급실 기본 정보
    @GET("getEgytBassInfoInqire")
    suspend fun getEmergencyBasic(
        @Query("HPID") HPID: String?,
        @Query("_type") _type: String = "json",
        @Query("serviceKey") serviceKey: String = BuildConfig.EMERGENCY_API_KEY
    ): EmergencyBasicResponse
}