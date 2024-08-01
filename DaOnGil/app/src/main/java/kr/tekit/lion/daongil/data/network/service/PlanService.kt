package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.plan.openSchedule.OpenPlanListResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.PlaceSearchResultsResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.briefScheduleInfo.BriefScheduleInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myMainSchedule.MyMainScheduleResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleElapsed.MyElapsedResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleUpcoming.MyUpcomingsResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.scheduleDetail.ScheduleDetailResponse
import kr.tekit.lion.daongil.data.network.AuthType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Tag

interface PlanService {

    // 공개 일정 정보
    @GET("plan/open")
    suspend fun getOpenPlanList(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Tag authType: AuthType = AuthType.NO_AUTH,
    ): OpenPlanListResponse

    // 장소이름 기반 여행지 검색 : size - 한 페이지 데이터 수, page - 페이지 넘버
    @GET("plan/search")
    suspend fun getPlaceSearchResults(
        @Query("word") word: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ) : PlaceSearchResultsResponse

    // 일정 작성
    @POST("plan")
    suspend fun addNewPlan(
        @Body newPlan: RequestBody,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    )

    // 일정 수정
    @PATCH("plan/{planId}")
    suspend fun modifySchedule(
        @Path("planId") planId: Long,
        @Body newPlan: RequestBody
    )

    // 내 일정 정보
    @GET("plan/my")
    suspend fun getMyMainSchedule(): MyMainScheduleResponse

    // 일정 간단한 정보 (한 개)
    @GET("plan/{planId}")
    suspend fun getBriefScheduleInfo(
        @Path("planId") planId: Long
    ) : BriefScheduleInfoResponse

    // 여행 일정 후기 작성
    @Multipart
    @POST("plan/review/{planId}")
    suspend fun addNewScheduleReview(
        @Path("planId") planId: Long,
        @Part("planReviewReq") scheduleReview: RequestBody,
        @Part images: List<MultipartBody.Part>
    )

    @Multipart
    @POST("plan/review/{planId}")
    suspend fun addNewScheduleReviewTextOnly(
        @Path("planId") planId: Long,
        @Part("planReviewReq") scheduleReview: RequestBody,
    )

    // 다가오는 일정 목록
    @GET("plan/my/not-complete")
    suspend fun getMyUpcomingScheduleList(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): MyUpcomingsResponse

    // 다녀온 일정 목록
    @GET("plan/my/complete")
    suspend fun getMyElapsedScheduleList(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): MyElapsedResponse

    // 여행 일정 상세보기 (로그인버전)
    @GET("plan/detail/{planId}")
    suspend fun getDetailScheduleInfo(
        @Path("planId") planId: Long
    ): ScheduleDetailResponse

    // 여행 일정 상세보기 (게스트버전)
    @GET("plan/guest/detail/{planId}")
    suspend fun getDetailScheduleInfoGuest(
        @Path("planId") planId: Long,
        @Tag authType: AuthType = AuthType.NO_AUTH
    ): ScheduleDetailResponse
}