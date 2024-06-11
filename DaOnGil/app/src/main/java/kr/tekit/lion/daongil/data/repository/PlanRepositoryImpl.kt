package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.repository.PlanRepository

class PlanRepositoryImpl(
    private val planDataSource: PlanDataSource
) : PlanRepository {
    override suspend fun getOpenPlanList(size: Int, page: Int): OpenPlan {
        return planDataSource.getOpenPlanList(size, page).toDomainModel()
    }
}