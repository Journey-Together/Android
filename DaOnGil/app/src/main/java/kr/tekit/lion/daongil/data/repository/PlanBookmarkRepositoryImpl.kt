package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlanBookmarkDataSource
import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.repository.PlanBookmarkRepository

class PlanBookmarkRepositoryImpl(
    private val planBookmarkDataSource: PlanBookmarkDataSource
) : PlanBookmarkRepository {
    override suspend fun getPlanBookmark(): List<PlanBookmark> {
        return planBookmarkDataSource.getPlanBookmark().toDomainModel()
    }
}