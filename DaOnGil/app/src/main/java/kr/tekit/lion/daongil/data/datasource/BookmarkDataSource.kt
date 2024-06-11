package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlaceBookmarkListResponse
import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlaceBookmarkResponse
import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlanBookmarkResponse
import kr.tekit.lion.daongil.data.network.service.BookmarkService
import okhttp3.RequestBody


class BookmarkDataSource(
    private val bookmarkService: BookmarkService
) {
    suspend fun getPlaceBookmarkList(): PlaceBookmarkListResponse {
        return bookmarkService.getPlaceBookmarkList()
    }

    suspend fun getPlaceBookmark(): PlaceBookmarkResponse {
        return bookmarkService.getPlaceBookmark()
    }

    suspend fun getPlanBookmark(): PlanBookmarkResponse {
        return bookmarkService.getPlanBookmark()
    }

    suspend fun updatePlaceBookmark(placeId: Long) {
        bookmarkService.updatePlaceBookmark(placeId)
    }

    suspend fun updatePlanBookmark(planId: Long) {
        bookmarkService.updatePlanBookmark(planId)
    }
}