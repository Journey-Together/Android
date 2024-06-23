package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest.DetailPlaceGuestResponse
import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.mainplace.MainPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.review.MyPlaceReviewResponse
import kr.tekit.lion.daongil.data.dto.remote.response.placeReview.PlaceReviewResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.SearchPlaceResponse
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Tag

interface PlaceService {
    @GET("place/{placeId}")
    suspend fun getPlaceDetailInfo(
        @Path("placeId") placeId: Long,
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): DetailPlaceResponse

    @GET("place/main")
    suspend fun getPlaceMainInfo(
        @Query("areacode") areacode : String,
        @Query("sigungucode") sigungucode : String
    ): MainPlaceResponse

    @GET("place/search")
    suspend fun searchPlace(
        @Query("category") category: String,
        @Query("query") query: String,
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("minX") minX: Double?,
        @Query("maxX") maxX: Double?,
        @Query("minY") minY: Double?,
        @Query("maxY") maxY: Double?,
        @Query("disabilityType") disabilityType: List<String>,
        @Query("detailFilter") detailFilter: List<String>,
        @Query("areacode") areaCode: String? = "",
        @Query("sigungucode") sigunguCode: String?  = "",
        @Query("arrange") arrange: String = "C",
    ): SearchPlaceResponse

    @GET("place/guest/{placeId}")
    suspend fun getPlaceDetailInfoGuest(
        @Path("placeId") placeId: Long,
        @Tag authType: AuthType = AuthType.NO_AUTH
    ): DetailPlaceGuestResponse

    @GET("place/review/{placeId}")
    suspend fun getPlaceReviewList(
        @Path("placeId") placeId: Long,
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Tag authType: AuthType = AuthType.NO_AUTH
    ): PlaceReviewResponse
  
   @GET("place/review/my")
    suspend fun getMyPlaceReview(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): MyPlaceReviewResponse
}