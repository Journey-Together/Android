package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.toMultiPartBodyList
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestBody
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo
import kr.tekit.lion.daongil.domain.model.MyElapsedSchedules
import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.domain.model.MyUpcomingSchedules
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.model.ReviewImg
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

    override suspend fun getMyUpcomingScheduleList(size: Int, page: Int): MyUpcomingSchedules {
        return planDataSource.getMyUpcomingScheduleList(size, page).toDomainModel()
    }

    override suspend fun getMyElapsedScheduleList(size: Int, page: Int): MyElapsedSchedules {
        return planDataSource.getMyElapsedScheduleList(size, page).toDomainModel()
    }
}