package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.NaverMapDataSource
import kr.tekit.lion.daongil.domain.model.ReverseGecodes
import kr.tekit.lion.daongil.domain.repository.NaverMapRepository

class NaverMapRepositoryImpl(
    private val naverMapDataSource: NaverMapDataSource
) : NaverMapRepository{
    override suspend fun getReverseGeoCode(coords: String): ReverseGecodes {
        return naverMapDataSource.getReverseGeoCode(coords).toDomainModel()
    }
}