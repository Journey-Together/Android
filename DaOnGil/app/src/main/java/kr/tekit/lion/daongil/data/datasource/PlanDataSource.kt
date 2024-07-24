package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.plan.openSchedule.OpenPlanListResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.PlaceSearchResultsResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.briefScheduleInfo.BriefScheduleInfoResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myMainSchedule.MyMainScheduleResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleElapsed.MyElapsedResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleUpcoming.MyUpcomingsResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.scheduleDetail.ScheduleDetailResponse
import kr.tekit.lion.daongil.data.network.service.PlanService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PlanDataSource(
    private val planService: PlanService
) {

    suspend fun getOpenPlanList(size: Int, page: Int): OpenPlanListResponse {
        return planService.getOpenPlanList(size, page)
    }

    suspend fun addNewPlan(request: RequestBody){
        planService.addNewPlan(request)
    }
    suspend fun getPlaceSearchResult(word: String, page: Int, size: Int) : PlaceSearchResultsResponse {
        return planService.getPlaceSearchResults(word, page, size)
    }

    suspend fun getMyMainSchedule(): MyMainScheduleResponse {
        return planService.getMyMainSchedule()
    }

    suspend fun getBriefScheduleInfo(planId: Long) : BriefScheduleInfoResponse {
        return planService.getBriefScheduleInfo(planId)
    }

    suspend fun addNewScheduleReview(
        planId: Long,
        scheduleReview: RequestBody,
        images: List<MultipartBody.Part>?
    ) {
        if(images.isNullOrEmpty()){
            planService.addNewScheduleReviewTextOnly(planId, scheduleReview)
        }else{
            planService.addNewScheduleReview(planId, scheduleReview, images)
        }
    }

    suspend fun getMyUpcomingScheduleList(size: Int, page: Int): MyUpcomingsResponse {
        return planService.getMyUpcomingScheduleList(size, page)
    }


    suspend fun getMyElapsedScheduleList(size: Int, page: Int): MyElapsedResponse {
        return planService.getMyElapsedScheduleList(size, page)
    }

    suspend fun getDetailScheduleInfo(planId: Long): ScheduleDetailResponse {
        return planService.getDetailScheduleInfo(planId)
    }

    suspend fun getDetailScheduleInfoGuest(planId: Long): ScheduleDetailResponse {
        return planService.getDetailScheduleInfoGuest(planId)
    }
}