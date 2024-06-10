package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceBookmarkDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.BookmarkService
import kr.tekit.lion.daongil.data.repository.PlaceBookmarkRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceBookmark

interface PlaceBookmarkRepository {
    suspend fun getPlaceBookmark() : List<PlaceBookmark>

    companion object {
        fun create(): PlaceBookmarkRepositoryImpl {
            return PlaceBookmarkRepositoryImpl(
                PlaceBookmarkDataSource(
                    RetrofitInstance.serviceProvider(BookmarkService::class.java)
                )
            )
        }
    }
}