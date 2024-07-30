package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlanService
import kr.tekit.lion.daongil.data.repository.PlanRepositoryImpl
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo
import kr.tekit.lion.daongil.domain.model.MyElapsedSchedules
import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.domain.model.MyUpcomingSchedules
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.model.ReviewImg
import kr.tekit.lion.daongil.domain.model.ScheduleDetailInfo
import kr.tekit.lion.daongil.domain.model.ScheduleDetailReview

interface PlanRepository {
    suspend fun getOpenPlanList(size: Int, page: Int): OpenPlan

    suspend fun addNewPlan(request: NewPlan)

    // 구현해야 할 메서드
    suspend fun getPlaceSearchResult(word: String, page: Int, size: Int) : PlaceSearchResult

    suspend fun getMyMainSchedule(): List<MyMainSchedule?>?

    suspend fun getBriefScheduleInfo(planId: Long) : BriefScheduleInfo

    suspend fun addNewScheduleReview(
        planId: Long,
        scheduleReview: NewScheduleReview,
        images: List<ReviewImg>
    )

    suspend fun getMyUpcomingScheduleList(size: Int, page: Int): MyUpcomingSchedules

    suspend fun getMyElapsedScheduleList(size: Int, page: Int): MyElapsedSchedules

    suspend fun getDetailScheduleInfo(planId: Long): ScheduleDetailInfo

    suspend fun getDetailScheduleInfoGuest(plandId: Long): ScheduleDetailInfo

    suspend fun getDetailScheduleReview(planId: Long): ScheduleDetailReview

    suspend fun getDetailScheduleReviewGuest(planId: Long): ScheduleDetailReview

    suspend fun deleteMyPlanReview(reviewId: Long)

    suspend fun updateMyPlanPublic(planId: Long)

    suspend fun deleteMyPlanSchedule(planId: Long)

    companion object{
        fun create(): PlanRepositoryImpl{
            return PlanRepositoryImpl(
                PlanDataSource(
                    RetrofitInstance.serviceProvider(PlanService::class.java)
                )
            )
        }
    }
}