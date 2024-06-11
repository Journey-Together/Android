package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.mainplace.MainPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.SearchPlaceResponse
import kr.tekit.lion.daongil.data.network.AuthType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
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
        @QueryMap request: Map<String, String?>,
        @Tag authType: AuthType = AuthType.NO_AUTH
    ): SearchPlaceResponse
}