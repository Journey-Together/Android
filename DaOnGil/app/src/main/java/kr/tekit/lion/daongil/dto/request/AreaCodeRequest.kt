package kr.tekit.lion.daongil.dto.request

import retrofit2.http.Query

data class AreaCodeRequest(
    @Query("pageNo") val pageNo: String,
    @Query("MobileOS") val mobileOs: String = "AND",
    @Query("MobileApp") val mobileApp: String = "DaOnGil",
    @Query("_type") val type: String = "json",
    @Query("serviceKey") val serviceKey: String
)


