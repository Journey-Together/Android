package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.NaverMapDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.NaverMapRepositoryImpl
import kr.tekit.lion.daongil.domain.model.ReverseGecodes

interface NaverMapRepository {
    suspend fun getReverseGeoCode(coords: String): ReverseGecodes

    companion object {
        fun create(): NaverMapRepositoryImpl{
            return NaverMapRepositoryImpl(
                NaverMapDataSource(
                    RetrofitInstance.naverMapService
                )
            )
        }
    }
}