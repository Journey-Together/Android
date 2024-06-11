package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlaceBookmarkListResponse
import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlaceBookmarkResponse
import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlanBookmarkResponse
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Tag

interface BookmarkService {
    @GET("bookmark/names")
    suspend fun getPlaceBookmarkList() : PlaceBookmarkListResponse

    @GET("bookmark/place")
    suspend fun getPlaceBookmark() : PlaceBookmarkResponse

    @GET("bookmark/plan")
    suspend fun getPlanBookmark() : PlanBookmarkResponse

    @PATCH("bookmark/place/{placeId}")
    suspend fun updatePlaceBookmark(
        @Path("placeId") placeId: Long
    )

    @PATCH("bookmark/plan/{planId}")
    suspend fun updatePlanBookmark(
        @Path("planId") planId: Long
    )
}