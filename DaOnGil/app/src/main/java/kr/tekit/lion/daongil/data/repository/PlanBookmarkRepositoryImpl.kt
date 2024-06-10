package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.BookmarkDataSource
import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.repository.PlanBookmarkRepository

class PlanBookmarkRepositoryImpl(
    private val planBookmarkDataSource: BookmarkDataSource
) : PlanBookmarkRepository {
    override suspend fun getPlanBookmark(): List<PlanBookmark> {
        return planBookmarkDataSource.getPlanBookmark().toDomainModel()
    }
}