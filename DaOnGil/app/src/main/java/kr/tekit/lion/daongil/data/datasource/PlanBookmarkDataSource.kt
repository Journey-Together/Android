package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlanBookmarkResponse
import kr.tekit.lion.daongil.data.network.service.BookmarkService

class PlanBookmarkDataSource(
    private val bookmarkService: BookmarkService
) {
    suspend fun getPlanBookmark(): PlanBookmarkResponse {
        return bookmarkService.getPlanBookmark()
    }
}