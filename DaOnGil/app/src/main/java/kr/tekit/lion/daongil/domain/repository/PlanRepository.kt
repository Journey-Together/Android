package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlanService
import kr.tekit.lion.daongil.data.repository.PlanRepositoryImpl
import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult

interface PlanRepository {
    suspend fun getOpenPlanList(size: Int, page: Int): OpenPlan

    suspend fun addNewPlan(request: NewPlan)

    // 구현해야 할 메서드
    suspend fun getPlaceSearchResult(word: String, page: Int, size: Int) : PlaceSearchResult

    suspend fun getMyMainSchedule(): List<MyMainSchedule?>?

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