package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.BookmarkDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.BookmarkService
import kr.tekit.lion.daongil.data.repository.BookmarkRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.model.PlanBookmark

interface BookmarkRepository {
    suspend fun getPlaceBookmark() : List<PlaceBookmark>

    suspend fun getPlanBookmark() : List<PlanBookmark>

    suspend fun updatePlaceBookmark(placeId: Long)

    companion object {
        fun create(): BookmarkRepositoryImpl {
            return BookmarkRepositoryImpl(
                BookmarkDataSource(
                    RetrofitInstance.serviceProvider(BookmarkService::class.java)
                )
            )
        }
    }
}