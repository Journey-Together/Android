package kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val address: String,
    val bookmarkNum: Int,
    val category: String,
    val disability: List<Int>,
    @Json(name = "imgae")
    val image: String,
    val mapX: String,
    val mapY: String,
    val name: String,
    val overview: String,
    val placeId: Long,
    val reviewList: List<ReviewGuestRes>?,
    val subDisability: List<SubDisabilityGuestRes>?
)