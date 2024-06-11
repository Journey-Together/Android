package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceSearchResultDataSource
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.repository.PlaceSearchResultRepository

class PlaceSearchResultRepositoryImpl (
    private val placeSearchResultDataSource: PlaceSearchResultDataSource
): PlaceSearchResultRepository{

    // Repository Interface에 구현한 getPlaceSearchResult 함수
    override suspend fun getPlaceSearchResult(
        word: String,
        page: Int,
        size: Int
    ): PlaceSearchResult {
        return placeSearchResultDataSource.getPlaceSearchResult(word, page, size).toDomainModel()
    }
}