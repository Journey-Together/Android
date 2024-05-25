package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.AreaBasedDataSource
import kr.tekit.lion.daongil.domain.model.AreaBased
import kr.tekit.lion.daongil.domain.repository.AreaBasedRepository

class AreaBasedRepositoryImpl(
    private val areaBasedDataSource: AreaBasedDataSource
): AreaBasedRepository {
    override suspend fun searchAllTouristSpot(contentId: String): List<AreaBased> {
        return areaBasedDataSource.getSearchByAreaResult(contentId).toDomainModel()
    }
}