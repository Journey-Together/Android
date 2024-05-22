package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.AreaBasedDataSource
import kr.tekit.lion.daongil.domain.model.KeywordSearch
import kr.tekit.lion.daongil.domain.repository.AreaBasedRepository

class AreaBasedRepositoryImpl(
    private val areaBasedDataSource: AreaBasedDataSource
): AreaBasedRepository {
    override suspend fun getAreaBasedSearch(contentTypeId: String, villageCode: String): List<KeywordSearch> {
        return areaBasedDataSource.getSearchByAreaResult(contentTypeId, villageCode).toDomainModel()
    }
}