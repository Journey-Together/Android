package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceSearchResultDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlanService
import kr.tekit.lion.daongil.data.repository.PlaceSearchResultRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult

interface PlaceSearchResultRepository {
    // 구현해야 할 메서드
    suspend fun getPlaceSearchResult(word: String, page: Int, size: Int) : PlaceSearchResult

    companion object{
        // PlaceSearchResultRepository 인스턴스 생성
        fun create(): PlaceSearchResultRepositoryImpl{
            return PlaceSearchResultRepositoryImpl(
                PlaceSearchResultDataSource(
                    RetrofitInstance.serviceProvider(PlanService::class.java)
                )
            )
        }
    }
}