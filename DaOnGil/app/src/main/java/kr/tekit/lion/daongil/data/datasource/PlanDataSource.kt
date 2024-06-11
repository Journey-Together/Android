package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.plan.OpenPlanListResponse
import kr.tekit.lion.daongil.data.network.service.PlanService

class PlanDataSource(
    private val planService: PlanService
) {

    suspend fun getOpenPlanList(size: Int, page: Int): OpenPlanListResponse {
        return planService.getOpenPlanList(size, page)
    }

}