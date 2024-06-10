package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceMainInfoDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.repository.PlaceMainInfoRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceMainInfo

interface PlaceMainInfoRepository {
    suspend fun getPlaceMainInfo(areaCode: String, sigunguCode: String): PlaceMainInfo

    companion object{
        fun create() : PlaceMainInfoRepositoryImpl{
            return PlaceMainInfoRepositoryImpl(
                PlaceMainInfoDataSource(
                    RetrofitInstance.serviceProvider(PlaceService::class.java)
                )
            )
        }
    }
}