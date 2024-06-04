package kr.tekit.lion.daongil.data.network.service

import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlaceService {
    @GET("v1/place/{placeId}")
    suspend fun getPlaceDetailInfo(
        @Path("placeId") placeId: Long,
        @Header("Authorization") accessToken: String = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5ZW9uODYyMUBuYXZlci5jb20iLCJyb2xlIjoiR0VORVJBTCIsImlhdCI6MTcxNzQ5ODI4NCwiZXhwIjoxNzE4MTAzMDg0fQ.UuIE8SgYky-PdMZL_quKf0Uk4k6JvLsOQYc08E73UFU"

    ): DetailPlaceResponse
}