package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlaceBookmarkResponse
import kr.tekit.lion.daongil.data.dto.remote.response.bookmark.PlanBookmarkResponse
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.GET
import retrofit2.http.Tag

interface BookmarkService {
    @GET("bookmark/place")
    suspend fun getPlaceBookmark(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ) : PlaceBookmarkResponse

    @GET("bookmark/plan")
    suspend fun getPlanBookmark(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ) : PlanBookmarkResponse
}