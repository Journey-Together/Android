package kr.tekit.lion.daongil.data.dto.remote.response.emergency.pharmacy


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "dutyAddr")
    val dutyAddr: String?,
    @Json(name = "dutyEtc")
    val dutyEtc: String?,
    @Json(name = "dutyMapimg")
    val dutyMapimg: String?,
    @Json(name = "dutyName")
    val dutyName: String?,
    @Json(name = "dutyTel1")
    val dutyTel1: String?,
    @Json(name = "dutyTime1c")
    val dutyTime1c: Int?,
    @Json(name = "dutyTime1s")
    val dutyTime1s: String?,
    @Json(name = "dutyTime2c")
    val dutyTime2c: Int?,
    @Json(name = "dutyTime2s")
    val dutyTime2s: String?,
    @Json(name = "dutyTime3c")
    val dutyTime3c: Int?,
    @Json(name = "dutyTime3s")
    val dutyTime3s: String?,
    @Json(name = "dutyTime4c")
    val dutyTime4c: Int?,
    @Json(name = "dutyTime4s")
    val dutyTime4s: String?,
    @Json(name = "dutyTime5c")
    val dutyTime5c: Int?,
    @Json(name = "dutyTime5s")
    val dutyTime5s: String?,
    @Json(name = "dutyTime6c")
    val dutyTime6c: Int?,
    @Json(name = "dutyTime6s")
    val dutyTime6s: String?,
    @Json(name = "dutyTime7c")
    val dutyTime7c: Int?,
    @Json(name = "dutyTime7s")
    val dutyTime7s: Int?,
    @Json(name = "dutyTime8c")
    val dutyTime8c: Int?,
    @Json(name = "dutyTime8s")
    val dutyTime8s: Int?,
    @Json(name = "hpid")
    val hpid: String?,
    @Json(name = "postCdn1")
    val postCdn1: Int?,
    @Json(name = "postCdn2")
    val postCdn2: Int?,
    @Json(name = "rnum")
    val rnum: Int?,
    @Json(name = "wgs84Lat")
    val wgs84Lat: Double?,
    @Json(name = "wgs84Lon")
    val wgs84Lon: Double?
)