package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceBookmarkDataSource
import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.repository.PlaceBookmarkRepository

class PlaceBookmarkRepositoryImpl(
    private val placeBookmarkDataSource: PlaceBookmarkDataSource
) : PlaceBookmarkRepository {
    override suspend fun getPlaceBookmark(): List<PlaceBookmark> {
        return placeBookmarkDataSource.getPlaceBookmark().toDomainModel()
    }
}