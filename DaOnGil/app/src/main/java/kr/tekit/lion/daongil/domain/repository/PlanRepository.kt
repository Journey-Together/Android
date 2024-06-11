package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlanService
import kr.tekit.lion.daongil.data.repository.PlanRepositoryImpl
import kr.tekit.lion.daongil.domain.model.OpenPlan

interface PlanRepository {
    suspend fun getOpenPlanList(size: Int, page: Int): OpenPlan

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