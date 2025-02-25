package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.toMultiPartBodyList
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestBody
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo
import kr.tekit.lion.daongil.domain.model.ModifiedScheduleReview
import kr.tekit.lion.daongil.domain.model.MyElapsedSchedules
import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.domain.model.MyUpcomingSchedules
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.model.ReviewImage
import kr.tekit.lion.daongil.domain.model.ReviewImg
import kr.tekit.lion.daongil.domain.model.ScheduleDetailInfo
import kr.tekit.lion.daongil.domain.model.ScheduleDetailReview
import kr.tekit.lion.daongil.domain.repository.PlanRepository

class PlanRepositoryImpl(
    private val planDataSource: PlanDataSource
) : PlanRepository {

    override suspend fun getOpenPlanList(size: Int, page: Int): OpenPlan {
        return planDataSource.getOpenPlanList(size, page).toDomainModel()
    }

    override suspend fun addNewPlan(request: NewPlan) {
        return planDataSource.addNewPlan(request.toRequestBody())
    }

    override suspend fun modifySchedule(planId: Long, request: NewPlan) {
        return planDataSource.modifySchedule(planId, request.toRequestBody())
    }

    override suspend fun getPlaceSearchResult(word: String, page: Int, size: Int
    ): PlaceSearchResult {
        return planDataSource.getPlaceSearchResult(word, page, size).toDomainModel()
    }

    override suspend fun getMyMainSchedule(): List<MyMainSchedule?>? {
        return planDataSource.getMyMainSchedule().toDomainModel()
    }

    override suspend fun getBriefScheduleInfo(planId: Long): BriefScheduleInfo {
        return planDataSource.getBriefScheduleInfo(planId).toDomainModel()
    }

    override suspend fun addNewScheduleReview(
        planId: Long,
        scheduleReview: NewScheduleReview,
        images: List<ReviewImg>
    ) {
        return planDataSource.addNewScheduleReview(
            planId,
            scheduleReview.toRequestBody(),
            images.toMultiPartBodyList()
        )
    }

    override suspend fun modifyScheduleReview(
        reviewId: Long,
        scheduleReview: ModifiedScheduleReview,
        images: List<ReviewImage>?
    ) {
        return planDataSource.modifyScheduleReview(
            reviewId,
            scheduleReview.toRequestBody(),
            images?.toMultiPartBodyList()
        )
    }

    override suspend fun getMyUpcomingScheduleList(size: Int, page: Int): MyUpcomingSchedules {
        return planDataSource.getMyUpcomingScheduleList(size, page).toDomainModel()
    }

    override suspend fun getMyElapsedScheduleList(size: Int, page: Int): MyElapsedSchedules {
        return planDataSource.getMyElapsedScheduleList(size, page).toDomainModel()
    }

    override suspend fun getDetailScheduleInfo(planId: Long): ScheduleDetailInfo {

        return planDataSource.getDetailScheduleInfo(planId).toDomainModel()
    }

    override suspend fun getDetailScheduleInfoGuest(planId: Long): ScheduleDetailInfo {
        return planDataSource.getDetailScheduleInfoGuest(planId).toDomainModel()
    }

    override suspend fun getDetailScheduleReview(planId: Long): ScheduleDetailReview {
        return planDataSource.getDetailScheduleReview(planId).toDomainModel()
    }

    override suspend fun getDetailScheduleReviewGuest(planId: Long): ScheduleDetailReview {
        return planDataSource.getDetailScheduleReviewGuest(planId).toDomainModel()
    }

    override suspend fun deleteMyPlanReview(reviewId: Long) {
        return planDataSource.deleteMyPlanReview(reviewId)
    }

    override suspend fun updateMyPlanPublic(planId: Long) {
        return planDataSource.updateMyPlanPublic(planId)
    }

    override suspend fun deleteMyPlanSchedule(planId: Long) {
        return planDataSource.deleteMyPlanSchedule(planId)
    }
}