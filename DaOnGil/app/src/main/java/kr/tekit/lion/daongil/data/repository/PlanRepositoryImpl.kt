package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlanDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestBody
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
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

    // Repository Interface에 구현한 getPlaceSearchResult 함수
    override suspend fun getPlaceSearchResult(
        word: String,
        page: Int,
        size: Int
    ): PlaceSearchResult {
        return planDataSource.getPlaceSearchResult(word, page, size).toDomainModel()
    }
}