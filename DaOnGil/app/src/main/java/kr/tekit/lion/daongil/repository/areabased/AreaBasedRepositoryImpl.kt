package kr.tekit.lion.daongil.repository.areabased

import kr.tekit.lion.daongil.datasource.AreaBasedDataSource
import kr.tekit.lion.daongil.model.KeywordSearch

class AreaBasedRepositoryImpl(
    private val areaBasedDataSource: AreaBasedDataSource
): AreaBasedRepository {
    override suspend fun getAreaBasedSearch(contentTypeId: String, villageCode: String): List<KeywordSearch> {
        return areaBasedDataSource.getSearchByAreaResult(contentTypeId, villageCode).toDomainModel()
    }
}