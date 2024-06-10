package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.plan.OpenPlanListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanService {

    // 공개 일정 정보
    @GET("plan/open")
    suspend fun getOpenPlanList(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): OpenPlanListResponse
}