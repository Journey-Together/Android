package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.plan.openSchedule.OpenPlanListResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.PlaceSearchResultsResponse
import kr.tekit.lion.daongil.data.dto.remote.response.plan.myMainSchedule.MyMainScheduleResponse
import kr.tekit.lion.daongil.data.network.service.PlanService
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

}