package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlaceBookmarkResponse
import kr.tekit.lion.daongil.data.network.service.BookmarkService


class PlaceBookmarkDataSource(
    private val bookmarkService: BookmarkService
) {
    suspend fun getPlaceBookmark(): PlaceBookmarkResponse {
        return bookmarkService.getPlaceBookmark()
    }
}