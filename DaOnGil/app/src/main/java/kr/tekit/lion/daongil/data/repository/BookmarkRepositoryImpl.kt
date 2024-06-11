package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.BookmarkDataSource
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl(
    private val bookmarkDataSource: BookmarkDataSource
) : BookmarkRepository {
    override suspend fun getPlaceBookmarkList(): List<BookmarkedPlace> {
        return bookmarkDataSource.getPlaceBookmarkList().toDomainModel()
    }

    override suspend fun getPlaceBookmark(): List<PlaceBookmark> {
        return bookmarkDataSource.getPlaceBookmark().toDomainModel()
    }

    override suspend fun getPlanBookmark(): List<PlanBookmark> {
        return bookmarkDataSource.getPlanBookmark().toDomainModel()
    }

    override suspend fun updatePlaceBookmark(placeId: Long) {
        return bookmarkDataSource.updatePlaceBookmark(placeId)
    }
}