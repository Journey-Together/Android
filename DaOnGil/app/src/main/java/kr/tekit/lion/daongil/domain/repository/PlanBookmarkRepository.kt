package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlanBookmarkDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.BookmarkService
import kr.tekit.lion.daongil.data.repository.PlanBookmarkRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlanBookmark

interface PlanBookmarkRepository {
    suspend fun getPlanBookmark() : List<PlanBookmark>

    companion object {
        fun create() : PlanBookmarkRepositoryImpl {
            return PlanBookmarkRepositoryImpl(
                PlanBookmarkDataSource(
                    RetrofitInstance.serviceProvider(BookmarkService::class.java)
                )
            )
        }
    }
}