package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.AreaBasedDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.AreaBasedRepositoryImpl
import kr.tekit.lion.daongil.domain.model.AreaBased

interface AreaBasedRepository {
    suspend fun searchAllTouristSpot(contentId: String): List<AreaBased>

    companion object{
        fun create(): AreaBasedRepositoryImpl{
            return AreaBasedRepositoryImpl(
                AreaBasedDataSource(RetrofitInstance.korWithService)
            )
        }
    }
}