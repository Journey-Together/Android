package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceService {
    @GET("place/{placeId}")
    suspend fun getPlaceDetailInfo(
        @Path("placeId") placeId: Long,
    ): DetailPlaceResponse
}